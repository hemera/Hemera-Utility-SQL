package hemera.utility.sql;

import java.sql.SQLException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * <code>SQLSSHSource</code> defines the immutable
 * structure representing a SQL data source connected
 * via a SSH tunnel.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class SQLSSHSource extends SQLSource {
	/**
	 * The SSH <code>Session</code> instance.
	 */
	private final Session session;

	/**
	 * Constructor of <code>SQLSSHSource</code>.
	 * @param key The <code>String</code> key used to
	 * identify the attached data source.
	 * @param host The <code>String</code> address of
	 * the host.
	 * @param port The <code>int</code> port of the
	 * host to connect to.
	 * @param dbName The <code>String</code> name of
	 * the database.
	 * @param dbUsername The <code>String</code> user
	 * name used to login to the database.
	 * @param dbPassword The <code>String</code> password
	 * used to login to the database.
	 * @param sshKey The <code>String</code> path to
	 * the SSH key file.
	 * @param sshUsername The <code>String</code>
	 * user name used login to the SSH host.
	 * @param localPort The <code>int</code> forwarding
	 * port number to use on the local machine.
	 * @throws JSchException If SSH tunnel setup failed.
	 */
	SQLSSHSource(final String key, final String host, final int port, final String dbName,
			final String dbUsername, final String dbPassword, final String sshKey,
			final String sshUsername, final int localPort) throws JSchException {
		super(key, "localhost", localPort, dbName, dbUsername, dbPassword);
		// Setup SSH tunnel.
		final JSch jsch = new JSch();
		jsch.addIdentity(sshKey);
		this.session = jsch.getSession(sshUsername, host);
		this.session.setTimeout(0);
		this.session.setConfig("StrictHostKeyChecking", "no");
		this.session.connect();
		this.session.setPortForwardingL(localPort, "127.0.0.1", port);
	}
	
	@Override
	public synchronized void close() throws SQLException {
		super.close();
		this.session.disconnect();
	}
}
