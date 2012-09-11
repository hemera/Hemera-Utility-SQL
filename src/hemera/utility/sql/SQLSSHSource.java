package hemera.utility.sql;

import java.io.File;
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
	 * @param sshPort The <code>int</code> port used
	 * to establish the SSH connection.
	 * @param sshUsername The <code>String</code> SSH
	 * user name used login to the SSH host.
	 * @param sshKey The SSH key <code>File</code>.
	 * @param dbPort The <code>int</code> port of the
	 * host database to connect to.
	 * @param dbName The <code>String</code> name of
	 * the database.
	 * @param dbUsername The <code>String</code> user
	 * name used to login to the database.
	 * @param dbPassword The <code>String</code> password
	 * used to login to the database.
	 * @param localPort The <code>int</code> forwarding
	 * port number to use on the local machine.
	 * @throws JSchException If SSH tunnel setup failed.
	 */
	SQLSSHSource(final String key, final String host, final int sshPort, final String sshUsername,
			final File sshKey, final int dbPort, final String dbName, final String dbUsername,
			final String dbPassword, final int localPort) throws JSchException {
		super(key, "localhost", localPort, dbName, dbUsername, dbPassword);
		// Setup SSH tunnel.
		final JSch jsch = new JSch();
		jsch.addIdentity(sshKey.getAbsolutePath());
		this.session = jsch.getSession(sshUsername, host, sshPort);
		this.session.setTimeout(0);
		this.session.setConfig("StrictHostKeyChecking", "no");
		this.session.connect();
		this.session.setPortForwardingL(localPort, "127.0.0.1", dbPort);
	}
	
	/**
	 * Constructor of <code>SQLSSHSource</code>.
	 * @param key The <code>String</code> key used to
	 * identify the attached data source.
	 * @param host The <code>String</code> address of
	 * the host.
	 * @param sshPort The <code>int</code> port used
	 * to establish the SSH connection.
	 * @param sshUsername The <code>String</code> SSH
	 * user name used login to the SSH host.
	 * @param sshPassword The <code>String</code> SSH
	 * password used to login to the SSH host.
	 * @param dbPort The <code>int</code> port of the
	 * host database to connect to.
	 * @param dbName The <code>String</code> name of
	 * the database.
	 * @param dbUsername The <code>String</code> user
	 * name used to login to the database.
	 * @param dbPassword The <code>String</code> password
	 * used to login to the database.
	 * @param localPort The <code>int</code> forwarding
	 * port number to use on the local machine.
	 * @throws JSchException If SSH tunnel setup failed.
	 */
	SQLSSHSource(final String key, final String host, final int sshPort, final String sshUsername,
			final String sshPassword, final int dbPort, final String dbName, final String dbUsername,
			final String dbPassword, final int localPort) throws JSchException {
		super(key, "localhost", localPort, dbName, dbUsername, dbPassword);
		// Setup SSH tunnel.
		final JSch jsch = new JSch();
		this.session = jsch.getSession(sshUsername, host, sshPort);
		this.session.setPassword(sshPassword);
		this.session.setTimeout(0);
		this.session.setConfig("StrictHostKeyChecking", "no");
		this.session.connect();
		this.session.setPortForwardingL(localPort, "127.0.0.1", dbPort);
	}
	
	@Override
	public synchronized void close() throws SQLException {
		super.close();
		this.session.disconnect();
	}
}
