/*
Copyright St�phane Georges Popoff, (D�cembre 2010 - mars 2014)

spopoff@rocketmail.com

Ce logiciel est un programme informatique servant � g�rer des habilitations.

Ce logiciel est r�gi par la licence [CeCILL|CeCILL-B|CeCILL-C] soumise au droit fran�ais et
respectant les principes de diffusion des logiciels libres. Vous pouvez
utiliser, modifier et/ou redistribuer ce programme sous les conditions
de la licence [CeCILL|CeCILL-B|CeCILL-C] telle que diffus�e par le CEA, le CNRS et l'INRIA
sur le site "http://www.cecill.info".

En contrepartie de l'accessibilit� au code source et des droits de copie,
de modification et de redistribution accord�s par cette licence, il n'est
offert aux utilisateurs qu'une garantie limit�e.  Pour les m�mes raisons,
seule une responsabilit� restreinte p�se sur l'auteur du programme,  le
titulaire des droits patrimoniaux et les conc�dants successifs.

A cet �gard  l'attention de l'utilisateur est attir�e sur les risques
associ�s au chargement,  � l'utilisation,  � la modification et/ou au
d�veloppement et � la reproduction du logiciel par l'utilisateur �tant
donn� sa sp�cificit� de logiciel libre, qui peut le rendre complexe �
manipuler et qui le r�serve donc � des d�veloppeurs et des professionnels
avertis poss�dant  des  connaissances  informatiques approfondies.  Les
utilisateurs sont donc invit�s � charger  et  tester  l'ad�quation  du
logiciel � leurs besoins dans des conditions permettant d'assurer la
s�curit� de leurs syst�mes et ou de leurs donn�es et, plus g�n�ralement,
� l'utiliser et l'exploiter dans les m�mes conditions de s�curit�.

Le fait que vous puissiez acc�der � cet en-t�te signifie que vous avez
pris connaissance de la licence [CeCILL|CeCILL-B|CeCILL-C], et que vous en avez accept� les
termes.
 */
package com.spopoff.jersey;

import eu.eidas.auth.engine.core.EidasSAMLEngineFactoryI;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * REST Web Service
 *
 * @author MRWC1264
 */
@Path("/maj")
public class MajResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MajResource
     */
    public MajResource() {
    }

    /**
     * Retrieves representation of an instance of com.spopoff.oidServlet.MajResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
    public String getText() {
        //TODO return proper representation object
        return "hello world!";
    }

  /** 
   * Return MIME Type. 
   */
  private static final String MIME_TYPE = "text/plain";
  
  /** 
   * The refresh's return string. 
   */
  private static final String RETURN_MSG = "Refresh operation launched";
    private static final String REFRESH_IN_PROGRESS = "A refresh operation is currently in progress";
    private static final String REFRESH_NOT_ALLOWED = "not allowed";
    private static final String LOCALHOST="127.0.0.1";//NOSONAR

    EidasSAMLEngineFactoryI nodeSamlEngineFactory;

  /**
   * Version Controller. 
   */
  private VersionControl vc = null;
    private boolean refreshPending=false;
  
  /**
   * The Logger object.
   */
  private static final Logger LOG = Logger.getLogger(MajResource.class.getClass());
    Thread worker=null;
  
  /**
   * REST method that receives the request for restarting the application
   * context.
   * 
   * @return A string indicating that the application context is restarting.
   */
  @GET
  @Path("/refresh")
  @Produces(MIME_TYPE)
  public String refresh(@Context HttpServletRequest req) {
      if(!isRefreshPossible(req)){
          return REFRESH_NOT_ALLOWED;
      }
      synchronized(MajResource.class) {
          if (refreshPending) {
              return REFRESH_IN_PROGRESS;
          }
          refreshPending=true;
      }


      worker = new Thread(new Runnable(){
          @Override
          public void run() {
              try {
                  restart();
                  if (vc != null) {
                      vc.updateVersion();
                  } else {
                      LOG.warn("Couldn't inject VersionControl!");
                  }
              }catch(Exception exc){
                  LOG.error("error during configuration reload: ", exc);
              }
              refreshPending=false;
              worker=null;
          }
      });
      worker.start();
      return RETURN_MSG;
  }
  
  /**
   * Reloads the spring application context.
   */
  private void restart() {
    LOG.debug("Restarting application context...");
    final ApplicationContext ctx =
      ContextLoader.getCurrentWebApplicationContext();
    ((ConfigurableApplicationContext) ctx).close();
    ((ConfigurableApplicationContext) ctx).refresh();
  }
  
  /**
   * Sets the VersionControl wrapper.
   * 
   * @param nVC The vc to set.
   */
  public void setVc(final VersionControl nVC) {
    this.vc = nVC;
  }
  
  /**
   * Gets the VersionControl wrapper.
   * 
   * @return the vc value.
   */
  public VersionControl getVc() {
    return vc;
  }


    public EidasSAMLEngineFactoryI getNodeSamlEngineFactory() {
        return nodeSamlEngineFactory;
    }

    public void setNodeSamlEngineFactory(EidasSAMLEngineFactoryI nodeSamlEngineFactory) {
        this.nodeSamlEngineFactory = nodeSamlEngineFactory;
        LOG.info("injection du samlEngine");
    }

    private boolean isRefreshPossible(HttpServletRequest req){
        String remoteAddress = req.getRemoteAddr();
        if(!LOCALHOST.equalsIgnoreCase(remoteAddress)){
            LOG.warn("trying to refresh the configuration from remote address "+remoteAddress );
            return false;
        }
        if(nodeSamlEngineFactory==null){
            LOG.error("SAMLEngine null - denying update");
            return true;
        }
        if(getNodeSamlEngineFactory().getActiveEngineCount(null)>0){
            LOG.warn("invalid active SAMLEngine count - denying update");
            return false;
        }
        LOG.info("Allowing configuration reload");
        return true;
    }
}
