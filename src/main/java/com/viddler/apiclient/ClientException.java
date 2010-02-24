/**
 * 
 */
package com.viddler.apiclient;

/**
 * Exception throw by API methods when client side error occurs (network problems, XML serialization
 * problems etc)
 * 
 * @author Maciej Dragan
 * 
 */
public class ClientException extends Exception {

  private static final long serialVersionUID = -916175809048463939L;

  public ClientException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClientException(String message) {
    super(message);
  }

}
