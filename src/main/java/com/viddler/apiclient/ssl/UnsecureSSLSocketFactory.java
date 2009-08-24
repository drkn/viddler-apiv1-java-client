/**
 * 
 */
package com.viddler.apiclient.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * @author Maciej Dragan
 *
 */
public class UnsecureSSLSocketFactory extends SSLSocketFactory {

  private SSLSocketFactory factory;

  public UnsecureSSLSocketFactory() {
    try {
      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(null, new TrustManager[] { new UnsecureX509TrustManager() }, new SecureRandom());
      factory = ctx.getSocketFactory();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static UnsecureSSLSocketFactory getDefault() {
    return new UnsecureSSLSocketFactory();
  }

  /* (non-Javadoc)
   * @see javax.net.ssl.SSLSocketFactory#createSocket(java.net.Socket, java.lang.String, int, boolean)
   */
  @Override
  public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
    return factory.createSocket(s, host, port, autoClose);
  }

  /* (non-Javadoc)
   * @see javax.net.ssl.SSLSocketFactory#getDefaultCipherSuites()
   */
  @Override
  public String[] getDefaultCipherSuites() {
    return factory.getDefaultCipherSuites();
  }

  /* (non-Javadoc)
   * @see javax.net.ssl.SSLSocketFactory#getSupportedCipherSuites()
   */
  @Override
  public String[] getSupportedCipherSuites() {
    return factory.getSupportedCipherSuites();
  }

  /* (non-Javadoc)
   * @see javax.net.SocketFactory#createSocket(java.lang.String, int)
   */
  @Override
  public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
    return factory.createSocket(host, port);
  }

  /* (non-Javadoc)
   * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int)
   */
  @Override
  public Socket createSocket(InetAddress host, int port) throws IOException {
    return factory.createSocket(host, port);
  }

  /* (non-Javadoc)
   * @see javax.net.SocketFactory#createSocket(java.lang.String, int, java.net.InetAddress, int)
   */
  @Override
  public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException,
      UnknownHostException {
    return factory.createSocket(host, port, localHost, localPort);
  }

  /* (non-Javadoc)
   * @see javax.net.SocketFactory#createSocket(java.net.InetAddress, int, java.net.InetAddress, int)
   */
  @Override
  public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
    return factory.createSocket(address, port, localAddress, localPort);
  }

}
