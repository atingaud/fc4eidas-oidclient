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
package com.spopoff.frconnect;

public class FcParamConfig {
	
	private String tokenUri;
	private String authorizationUri;
	private String userInfoUri;
	private String clientId;
	private String clientSecret;
	private String redirectUri;
	private String scope;
	private String state;
        private String nonce;
	private String verifParameterId;
	private String verifParameterValue;
        private String logoutUri;
	
	public FcParamConfig(String tokenUri, 
			String authorizationUri, 
			String redirectUri,
			String userInfoUri, 
			String clientId, 
			String clientSecret,
			String scope,
			String state,
                        String nonce,
                        String verifParameterId,
                        String verifParameterValue,
                        String logoutUri) {
		super();
		this.tokenUri = tokenUri;
		this.authorizationUri = authorizationUri;
		this.redirectUri = redirectUri;
		this.userInfoUri = userInfoUri;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.scope = scope;
		this.state = state;
		this.nonce = nonce;
		this.verifParameterId = verifParameterId;
		this.verifParameterValue = verifParameterValue;
                this.logoutUri = logoutUri;
	}

    public String getLogoutUri() {
        return logoutUri;
    }

	public String getTokenUri() {
		return tokenUri;
	}

	public void setTokenUri(String tokenUri) {
		this.tokenUri = tokenUri;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getAuthorizationUri() {
		return authorizationUri;
	}

	public void setAuthorizationUri(String authorizationUri) {
		this.authorizationUri = authorizationUri;
	}

	public String getUserInfoUri() {
		return userInfoUri;
	}

	public void setUserInfoUri(String userInfoUri) {
		this.userInfoUri = userInfoUri;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getState() {
		return state;
	}
	public String getNonce() {
		return nonce;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getVerifParameterId() {
		return verifParameterId;
	}

	public void setVerifParameterId(String verifParameterId) {
		this.verifParameterId = verifParameterId;
	}

	public String getVerifParameterValue() {
		return verifParameterValue;
	}

	public void setVerifParameterValue(String verifParameterValue) {
		this.verifParameterValue = verifParameterValue;
	}
	
	
	
}
