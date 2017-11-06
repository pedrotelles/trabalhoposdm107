package br.com.inatel.jersey;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.StringTokenizer;
public class AuthenticationService {
	public boolean authenticate(String authCredentials) throws SQLException {
		if (null == authCredentials)
			return false;
		// header value format will be "Basic encoded string" for
		// Basic
		// authentication. Example "Basic YWRtaW46YWRtaW4="
		final String encodedUserPassword = authCredentials.replaceFirst("Basic"+ " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes =
					Base64.getDecoder().decode(encodedUserPassword);
			usernameAndPassword = new String(decodedBytes,
					"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		final StringTokenizer tokenizer = new
				StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();
		// we have fixed the userid and password as admin
		// call some UserService/LDAP here
		UsuarioDAO dao = new UsuarioDAO();
		
		boolean authenticationStatus = dao.checkUsuario(username,password);
		return authenticationStatus;
	}
}
