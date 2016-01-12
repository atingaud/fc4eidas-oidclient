/*
Copyright St�phane Georges Popoff, (D�cembre 2010 - mars 2016)

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
package com.spopoff.action;



import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.interceptor.ParameterAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginAction extends ActionSupport implements ParameterAware {

    private static final long serialVersionUID = -7243683543548722148L;
    private static final Logger LOG = LoggerFactory.getLogger(LoginAction.class);
    private String samlToken;
    private String encryptAssertion;
    private String username;
    private String callback = "...";
    private Map<String, String[]> maps;


    @Override
    public String execute(){
        LOG.debug("Welcome in action LOGIN, back to="+callback);
        if(maps==null){
            LOG.error("erreur param null");
            return Action.ERROR;
        }
        if(maps.isEmpty()){
            LOG.error("erreur param vide");
            return Action.ERROR;
        }
        try {
            this.samlToken = maps.get("samlToken")[0];
            this.username = maps.get("username")[0];
            this.callback = maps.get("callback")[0];
        } catch (Exception e) {
            LOG.error("erreur recup param="+e);
            return Action.ERROR;
        }
        LOG.debug("Bye, back to="+callback);
        return Action.SUCCESS;
    }

    /**
     * @param samlToken the samlToken to set
     */
    public void setSamlToken(String samlToken) {
            this.samlToken = samlToken;
    }

    /**
     * @return the samlToken
     */
    public String getSamlToken() {
            return samlToken;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
            this.username = username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
            return username;
    }

    /**
     * @param callback the callback to set
     */
    public void setCallback(String callback) {
            this.callback = callback;
    }

    /**
     * @return the callback
     */
    public String getCallback() {
            return callback;
    }

    public String getEncryptAssertion() {
            return encryptAssertion;
    }

    public void setEncryptAssertion(String encryptAssertion) {
            this.encryptAssertion = encryptAssertion;
    }

    public void setParameters(Map<String, String[]> maps) {
        this.maps = maps;
    }
}
