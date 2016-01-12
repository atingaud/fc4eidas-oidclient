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
package com.spopoff.eidas;

import eu.eidas.auth.engine.EIDASSAMLEngine;
import eu.eidas.engine.exceptions.EIDASSAMLEngineException;
import eu.eidas.samlengineconfig.CertificateConfigurationManager;

import org.apache.log4j.Logger;

public class IDPUtil {
    /**
     * name of the property which switches idp metadata on and off
     */
    public static final String ACTIVE_METADATA_CHECK="idp.metadata.check";
    private static final Logger logger = Logger.getLogger(IDPUtil.class.getName());

    private static final String SAML_ENGINE_LOCATION_VAR="IDP_CONF_LOCATION";
    static CertificateConfigurationManager idpSamlEngineConfig=null;
    public static EIDASSAMLEngine createSAMLEngine(String samlEngineName) throws EIDASSAMLEngineException {
        if(idpSamlEngineConfig==null && System.getenv(SAML_ENGINE_LOCATION_VAR)!=null){
            idpSamlEngineConfig = ApplicationContextProvider.getApplicationContext().getBean(CertificateConfigurationManager.class);
            idpSamlEngineConfig.setLocation(getLocation(System.getenv(SAML_ENGINE_LOCATION_VAR)));
            logger.info("retrieving config from "+System.getenv(SAML_ENGINE_LOCATION_VAR));
        }
        if(idpSamlEngineConfig != null && idpSamlEngineConfig.isActive() && idpSamlEngineConfig.getConfiguration() != null && !idpSamlEngineConfig.getConfiguration().isEmpty()){
            return EIDASSAMLEngine.createSAMLEngine(samlEngineName,idpSamlEngineConfig);
        }
        else {
            return EIDASSAMLEngine.createSAMLEngine(samlEngineName);
        }

    }

    private static final String[] PATH_PREFIXES={"file://", "file:/","file:" };
    private static String getLocation(String location){
        if (location!=null){
            for(String prefix:PATH_PREFIXES){
                if(location.startsWith(prefix)){
                    return location.substring(prefix.length());
                }
            }
        }
        return location;
    }
}
