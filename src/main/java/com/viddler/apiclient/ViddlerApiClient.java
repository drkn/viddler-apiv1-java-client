/**
 * 
 */
package com.viddler.apiclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.viddler.apiclient.responses.ApiInfo;
import com.viddler.apiclient.responses.ApiResponse;
import com.viddler.apiclient.responses.Auth;
import com.viddler.apiclient.responses.Comment;
import com.viddler.apiclient.responses.Error;
import com.viddler.apiclient.responses.Upload;
import com.viddler.apiclient.responses.User;
import com.viddler.apiclient.responses.Video;
import com.viddler.apiclient.responses.VideoList;
import com.viddler.apiclient.responses.VideoStatus;

/**
 * Java Viddler API Client main class. Basic usage:
 * 
 * <pre>
 * ViddlerApiClient client = new ViddlerApiClient();
 * ApiInfo apiInfo = client.viddlerApiGetInfo();
 * </pre>
 * 
 * For detailed methods and method parameters description see Viddler API
 * documentation page at
 * http://wiki.developers.viddler.com/index.php/Viddler_API
 * 
 * Note: This is first public release of Java Viddler API Client which may still
 * contain bugs and missing javadocs. If you have any problems or questions fell
 * free to contact me at:
 * 
 * <pre>
 * Viddler: 
 * - http://www.viddler.com/drkn/
 * - http://www.viddler.com/forums/support/
 * - http://www.viddler.com/groups/developers/
 * </pre>
 * 
 * @author Maciej Dragan
 * 
 */
public class ViddlerApiClient {

  public static final String ENDPOINT = "http://api.viddler.com/rest/v1/";
  public static final String CLIENTVERSION = "1.1";
  public static final int ERRORCODE_SESSION_INVALID = 9;

  private Credentials credentials;
  private String apiKey;
  private String endpoint = ENDPOINT;
  private HttpClient httpClient;
  private Mapping mapping = new Mapping();
  private boolean reauthenticate = true;

  /**
   * Create new API Client instance with given apiKey
   * 
   * @param apiKey
   * @throws ClientException
   */
  public ViddlerApiClient(String apiKey) throws ClientException {
    if (isBlank(apiKey)) {
      throw new ClientException("API key cannot be blank");
    }
    this.apiKey = apiKey;
    httpClient = new HttpClient();
    try {
      mapping.loadMapping(getClass().getClassLoader().getResource("com/viddler/apiclient/mapping.xml"));
    } catch (IOException e) {
      throw new ClientException("Could not load client configuration", e);
    } catch (MappingException e) {
      throw new ClientException("Could not load client configuration", e);
    }
  }

  /**
   * Set proxy for http connection
   * 
   * @param host
   *          Proxy server host
   * @param port
   *          Proxy server port
   */
  public void setProxy(String host, int port) {
    if (host != null && port > 0) {
      httpClient.getHostConfiguration().setProxy(host, port);
    }
  }

  /**
   * Set credentials for methods which require authentication
   * 
   * @param username
   * @param password
   */
  public void setCredentials(String username, String password) {
    this.credentials = new Credentials(username, password);
  }

  public Credentials getCredentials() {
    return credentials;
  }

  /**
   * Set custom API endpoint (generally for testing purposes). Changing endpoint
   * will reset your current credentials
   * 
   * @param endpoint
   */
  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
    this.credentials = null;
  }

  /*
   * API METHODS
   */

  /**
   * viddler.api.getInfo
   * 
   * @throws ApiException
   * @throws ClientException
   */
  public ApiInfo viddlerApiGetInfo() throws ClientException, ApiException {
    return unmarshal(get("viddler.api.getInfo"), ApiInfo.class);
  }

  /**
   * viddler.users.auth
   * 
   * @throws ClientException
   * @throws ApiException
   */
  public void viddlerUsersAuth() throws ClientException, ApiException {
    if (credentials == null) {
      throw new ClientException("This method requires authentication but no credentials were set");
    }
    // Clear current session
    credentials.setSessionid(null);
    Auth response = (Auth) unmarshal(post("viddler.users.auth", new String[][] { { "user", credentials.getUsername() },
        { "password", credentials.getPassword() } }, false), Auth.class);
    // Set new session
    credentials.setSessionid(response.getSessionid());
  }

  /**
   * Enables one method call API login. This method is a wrapper for
   * <code>setCredentials</code> and <code>viddlerUsersAuth</code> methods
   * 
   * @see #setCredentials(String, String)
   * @see #viddlerUsersAuth()
   * 
   * @param username
   * @param password
   * @throws ClientException
   * @throws ApiException
   */
  public void viddlerUsersAuth(String username, String password) throws ClientException, ApiException {
    setCredentials(username, password);
    viddlerUsersAuth();
  }

  /**
   * viddler.users.register
   * 
   * @param username
   * @param password
   * @param email
   * @param firstname
   * @param lastname
   * @param question
   * @param answer
   * @param lang
   * @param company
   * @param termsAccepted
   * @return true if user was successfuly created, false otherwise
   * @throws ClientException
   * @throws ApiException
   */
  public boolean viddlerUsersRegister(String username, String password, String email, String firstname,
      String lastname, String question, String answer, String lang, String company, boolean termsAccepted)
      throws ClientException, ApiException {
    User user = unmarshal(post("viddler.users.register",
        new String[][] { { "user", username }, { "email", email }, { "fname", firstname }, { "lname", lastname },
            { "password", password }, { "question", question }, { "answer", answer }, { "lang", lang },
            { "termsaccepted", termsAccepted ? "1" : "0" }, { "company", company } }, false), User.class);
    return user != null && user.getUsername().equals(username);
  }

  /**
   * viddler.users.getProfile
   * 
   * @param username
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public User viddlerUsersGetProfile(String username) throws ClientException, ApiException {
    return unmarshal(get("viddler.users.getProfile", new String[][] { { "user", username } }, false), User.class);
  }

  /**
   * viddler.users.setProfile
   * 
   * If you don't want to change specific field - pass null as value
   * 
   * @param firstName
   * @param lastName
   * @param birthdate
   * @param aboutMe
   * @param gender
   * @param company
   * @param city
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public User viddlerUsersSetProfile(String firstName, String lastName, Date birthdate, String aboutMe, String gender,
      String company, String city) throws ClientException, ApiException {
    ParametersMap<String, Serializable> pm = new ParametersMap<String, Serializable>();
    pm.put("first_name", firstName);
    pm.put("last_name", lastName);
    pm.put("birthdate", birthdate);
    pm.put("about_me", aboutMe);
    pm.put("gender", gender);
    pm.put("company", company);
    pm.put("city", city);
    return unmarshal(post("viddler.users.setProfile", pm.serialize(), true), User.class);
  }

  /**
   * viddler.users.setOptions
   * 
   * This method has limited access to usually partner level
   * 
   * If you don't want to change specific field - pass null as value
   * 
   * @param showAccount
   * @param taggingEnabled
   * @param commentingEnabled
   * @param showRelatedVideos
   * @param embeddingEnabled
   * @param clickingThroughEnabled
   * @param emailThisEnabled
   * @param trackbacksEnabled
   * @param favouritesEnabled
   * @param customLogoEnabled
   * @return Number of options which have been changed
   * @throws ApiException
   * @throws ClientException
   * @throws DOMException
   * @throws NumberFormatException
   */
  public int viddlerUsersSetOptions(Boolean showAccount, Boolean taggingEnabled, Boolean commentingEnabled,
      Boolean showRelatedVideos, Boolean embeddingEnabled, Boolean clickingThroughEnabled, Boolean emailThisEnabled,
      Boolean trackbacksEnabled, Boolean favouritesEnabled, Boolean customLogoEnabled) throws NumberFormatException,
      DOMException, ClientException, ApiException {

    ParametersMap<String, Serializable> pm = new ParametersMap<String, Serializable>();
    pm.put("show_account", showAccount);
    pm.put("tagging_enabled", taggingEnabled);
    pm.put("commenting_enabled", commentingEnabled);
    pm.put("show_related_videos", showRelatedVideos);
    pm.put("embedding_enabled", embeddingEnabled);
    pm.put("clicking_through_enabled", clickingThroughEnabled);
    pm.put("email_this_enabled", emailThisEnabled);
    pm.put("trackbacks_enabled", trackbacksEnabled);
    pm.put("favourites_enabled", favouritesEnabled);
    pm.put("custom_logo_enabled", customLogoEnabled);

    return Integer.parseInt(post("viddler.users.setOptions", pm.serialize(), true).getFirstChild().getFirstChild()
        .getNodeValue());
  }

  /**
   * viddler.videos.getRecordToken
   * 
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public String viddlerVideosGetRecordToken() throws ClientException, ApiException {
    return get("viddler.videos.getRecordToken", null, true).getFirstChild().getFirstChild().getNodeValue();
  }

  /**
   * 
   * viddler.videos.getDetails
   * 
   * @param videoid
   * @param useSessionId
   *          Send current crendentials with request? This allows to get private
   *          information about currently logged in user's videos
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public Video viddlerVideosGetDetails(String videoid, boolean useSessionId) throws ClientException, ApiException {
    return unmarshal(get("viddler.videos.getDetails", new String[][] { { "video_id", videoid } }, useSessionId),
        Video.class);
  }

  /**
   * viddler.videos.getDetailsByUrl
   * 
   * @param url
   * @param useSessionId
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public Video viddlerVideosGetDetailsByUrl(String url, boolean useSessionId) throws ClientException, ApiException {
    return unmarshal(get("viddler.videos.getDetailsByUrl", new String[][] { { "url", url } }, useSessionId),
        Video.class);
  }

  /**
   * viddler.videos.getStatus
   * 
   * @param videoid
   * @param useSessionId
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public VideoStatus viddlerVideosGetStatus(String videoid, boolean useSessionId) throws ClientException, ApiException {
    return unmarshal(get("viddler.videos.getStatus", new String[][] { { "video_id", videoid } }, useSessionId),
        VideoStatus.class);
  }

  /**
   * viddler.videos.getByUser
   * 
   * @param usernames
   * @param page
   * @param perPage
   * @param useSessionId
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public VideoList viddlerVideosGetByUser(String[] usernames, Integer page, Integer perPage, boolean useSessionId)
      throws ClientException, ApiException {
    ParametersMap<String, Serializable> map = new ParametersMap<String, Serializable>();
    map.put("user", usernames);
    map.put("page", page);
    map.put("per_page", perPage);
    return unmarshal(get("viddler.videos.getByUser", map.serialize(), useSessionId), VideoList.class);
  }

  /**
   * viddler.videos.getByUser
   * 
   * @param username
   * @param page
   * @param perPage
   * @param useSessionId
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public VideoList viddlerVideosGetByUser(String username, Integer page, Integer perPage, boolean useSessionId)
      throws ClientException, ApiException {
    return viddlerVideosGetByUser(new String[] { username }, page, perPage, useSessionId);
  }

  /**
   * viddler.videos.getFeatured
   * 
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public VideoList viddlerVideosGetFeatured() throws ClientException, ApiException {
    return unmarshal(get("viddler.videos.getFeatured", null, false), VideoList.class);
  }

  /**
   * viddler.videos.getByTag
   * 
   * @param tag
   * @param page
   * @param perPage
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public VideoList viddlerVideosGetByTag(String tag, Integer page, Integer perPage) throws ClientException,
      ApiException {
    ParametersMap<String, Serializable> map = new ParametersMap<String, Serializable>();
    map.put("tag", tag);
    map.put("page", page);
    map.put("per_page", perPage);
    return unmarshal(get("viddler.videos.getByTag", map.serialize(), false), VideoList.class);
  }

  /**
   * viddler.videos.delete
   * 
   * @param videoid
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public boolean viddlerVideosDelete(String videoid) throws ClientException, ApiException {
    return "success".equals(post("viddler.videos.delete", new String[][] { { "video_id", videoid } }, true)
        .getFirstChild().getNodeName());
  }

  /**
   * viddler.videos.upload
   * 
   * @param file
   * @param title
   * @param description
   * @param tags
   * @param forceMakePublic
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public Video viddlerVideosUpload(File file, String title, String description, String[] tags, Boolean forceMakePublic)
      throws ClientException, ApiException {
    return viddlerVideosUpload(null, file, title, description, tags, forceMakePublic);
  }

  /**
   * viddler.videos.upload
   * 
   * @param file
   * @param title
   * @param description
   * @param tags
   * @param forceMakePublic
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public Video viddlerVideosUpload(Upload upload, File file, String title, String description, String[] tags,
      Boolean forceMakePublic) throws ClientException, ApiException {
    checkAuth();
    String serializedTags = serializeTags(tags);
    Document doc = null;
    try {
      doc = execute(prepareUpload(upload, file, title, description, serializedTags, forceMakePublic));
    } catch (ApiException e) {
      if (e.getError().getCode() == ERRORCODE_SESSION_INVALID && reauthenticate) {
        viddlerUsersAuth();
      } else {
        throw e;
      }
      doc = execute(prepareUpload(upload, file, title, description, serializedTags, forceMakePublic));
    }
    return unmarshal(doc, Video.class);
  }

  /**
   * viddler.videos.prepareUpload
   * 
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public Upload viddlerVideosPrepareUpload() throws ClientException, ApiException {
    return (Upload) unmarshal(get("viddler.videos.prepareUpload", null, true), Upload.class);
  }

  /**
   * Helper for preparing upload POST HTTP request
   * 
   * @param file
   * @param title
   * @param description
   * @param tags
   * @param makePublic
   * @return
   * @throws ClientException
   */
  private PostMethod prepareUpload(Upload upload, File file, String title, String description, String tags,
      Boolean makePublic) throws ClientException {
    PostMethod post = new PostMethod(upload != null ? upload.getEndpoint() : endpoint);
    try {
      post.setRequestEntity(new MultipartRequestEntity(new Part[] { new StringPart("api_key", apiKey),
          new StringPart("method", "viddler.videos.upload"),
          new StringPart("make_public", makePublic != null && makePublic.booleanValue() ? "1" : "0"),
          new StringPart("sessionid", getCredentials().getSessionid()), new StringPart("title", title),
          new StringPart("tags", tags), new StringPart("description", description), new StringPart("category", ""),
          new FilePart("file", file) }, post.getParams()));
      return post;
    } catch (FileNotFoundException e) {
      throw new ClientException("Upload file not found", e);
    }
  }

  /**
   * viddler.videos.setDetails
   * 
   * All parameters except videoid are optional. If you don't want to change
   * anything, just pass <code>null</code>
   * 
   * @param videoid
   * @param title
   * @param description
   * @param tags
   * @param viewPerm
   * @param viewUsers
   * @param viewUseSecret
   * @param embedPerm
   * @param embedUsers
   * @param commentingPerm
   * @param commentingUsers
   * @param downloadPerm
   * @param downloadUsers
   * @param taggingPerm
   * @param taggingUsers
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public Video viddlerVideosSetDetails(String videoid, String title, String description, String[] tags,
      PermissionType viewPerm, String[] viewUsers, Boolean viewUseSecret, PermissionType embedPerm,
      String[] embedUsers, PermissionType commentingPerm, String[] commentingUsers, PermissionType downloadPerm,
      String[] downloadUsers, PermissionType taggingPerm, String[] taggingUsers) throws ClientException, ApiException {
    ParametersMap<String, Serializable> map = new ParametersMap<String, Serializable>();
    map.put("video_id", videoid);
    map.put("title", title);
    map.put("description", description);
    map.put("tags", serializeTags(tags));
    map.put("view_perm", viewPerm);
    map.put("view_users", viewUsers);
    map.put("view_use_secret", viewUseSecret);
    map.put("embed_perm", embedPerm);
    map.put("embed_users", embedUsers);
    map.put("commenting_perm", commentingPerm);
    map.put("commenting_users", commentingUsers);
    map.put("download_perm", downloadPerm);
    map.put("download_users", downloadUsers);
    map.put("tagging_perm", taggingPerm);
    map.put("tagging_users", taggingUsers);
    return unmarshal(post("viddler.videos.setDetails", map.serialize(), true), Video.class);
  }

  /**
   * 
   * @param videoid
   * @param permalink
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public boolean viddlerVideosSetPermalink(String videoid, String permalink) throws ClientException, ApiException {
    return "success".equals(post("viddler.videos.setPermalink",
        new String[][] { { "video_id", videoid }, { "permalink", permalink } }, true).getFirstChild().getNodeName());
  }

  /**
   * viddler.videos.comments.add
   * 
   * @param videoid
   * @param comment
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public Comment viddlerVideosCommentsAdd(String videoid, String comment) throws ClientException, ApiException {
    return unmarshal(post("viddler.videos.comments.add",
        new String[][] { { "video_id", videoid }, { "text", comment } }, true), Comment.class);
  }

  /**
   * viddler.videos.comments.remove
   * 
   * @param commentId
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  public boolean viddlerVideoscommentsRemove(int commentId) throws ClientException, ApiException {
    return "success".equals(post("viddler.videos.comments.remove", new String[][] { { "comment_id", "" + commentId } },
        true).getFirstChild().getNodeType());
  }

  /*
   * INTERNAL METHODS
   */

  /**
   * Check if current session is logged in and try to login if not so
   */
  private void checkAuth() throws ClientException, ApiException {
    if (credentials == null || credentials.getSessionid() == null) {
      viddlerUsersAuth();
    }
  }

  /**
   * Send request using HTTP GET method
   * 
   * @param apiMethods
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  private Document get(String apiMethod) throws ClientException, ApiException {
    return get(apiMethod, null, false);
  }

  /**
   * Send request using HTTP GET method
   * 
   * @param apiMethods
   * @param params
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  private Document get(String apiMethod, String[][] params, boolean requireAuth) throws ClientException, ApiException {
    try {
      return execute(prepareGet(apiMethod, params, requireAuth));
    } catch (ApiException e) {
      if (e.getError().getCode() == ERRORCODE_SESSION_INVALID && reauthenticate) {
        viddlerUsersAuth();
      } else {
        throw e;
      }
      return execute(prepareGet(apiMethod, params, requireAuth));
    }
  }

  /**
   * Prepare POST HTTP request
   * 
   * @param apiMethod
   * @param params
   * @param requireAuth
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  private GetMethod prepareGet(String apiMethod, String[][] params, boolean requireAuth) throws ClientException,
      ApiException {
    GetMethod get = new GetMethod(endpoint);
    List<NameValuePair> nvps = prepareParameters(apiMethod, params, requireAuth);
    get.setQueryString(nvps.toArray(new NameValuePair[nvps.size()]));
    return get;
  }

  /**
   * Send request using HTTP POST method
   * 
   * @param apiMethod
   * @param params
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  private Document post(String apiMethod, String[][] params, boolean requireAuth) throws ClientException, ApiException {
    try {
      return execute(preparePost(apiMethod, params, requireAuth));
    } catch (ApiException e) {
      if (e.getError().getCode() == ERRORCODE_SESSION_INVALID && reauthenticate) {
        viddlerUsersAuth();
      } else {
        throw e;
      }
      return execute(preparePost(apiMethod, params, requireAuth));
    }
  }

  /**
   * Prepare POST HTTP request
   * 
   * @param apiMethod
   * @param params
   * @param requireAuth
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  private PostMethod preparePost(String apiMethod, String[][] params, boolean requireAuth) throws ClientException,
      ApiException {
    PostMethod post = new PostMethod(endpoint);
    List<NameValuePair> nvps = prepareParameters(apiMethod, params, requireAuth);
    post.setRequestBody(nvps.toArray(new NameValuePair[nvps.size()]));
    return post;
  }

  /**
   * Prepare parameters for HTTP method call
   * 
   * @param apiMethod
   * @param params
   * @param requireAuth
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  private List<NameValuePair> prepareParameters(String apiMethod, String[][] params, boolean requireAuth)
      throws ClientException, ApiException {
    List<NameValuePair> nvps = nvps(params);
    nvps.add(new NameValuePair("api_key", apiKey));
    nvps.add(new NameValuePair("method", apiMethod));
    if (requireAuth) {
      checkAuth();
      nvps.add(new NameValuePair("sessionid", credentials.getSessionid()));
    }
    return nvps;
  }

  /**
   * Send HTTP request
   * 
   * @param method
   * @return
   * @throws ClientException
   * @throws ApiException
   */
  private Document execute(HttpMethod method) throws ClientException, ApiException {
    try {
      method.setRequestHeader("User-Agent", "Java Viddler API Client/" + CLIENTVERSION + " ("
          + System.getProperty("os.name") + " " + System.getProperty("os.version") + "; Java "
          + System.getProperty("java.vm.version") + "; " + System.getProperty("user.language") + ")");
      httpClient.executeMethod(method);
      Header contentTypeHeader = method.getResponseHeader("Content-Type");
      String contentType = contentTypeHeader != null ? contentTypeHeader.getValue() : null;
      if (!"text/xml".equals(contentType)) {
        throw new ClientException("Invalid API response contentType: " + contentType + ". Should be text/xml.");
      }

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db;
      try {
        db = dbf.newDocumentBuilder();
        Document doc = db.parse(method.getResponseBodyAsStream());
        if ("error".equals(doc.getFirstChild().getNodeName())) {
          throw new ApiException(unmarshal(doc, Error.class));
        }
        return doc;
      } catch (ParserConfigurationException e) {
        throw new ClientException("There was a problem initializing XML parser", e);
      } catch (SAXException e) {
        throw new ClientException("There was a problem parsing API response", e);
      }
    } catch (HttpException e) {
      throw new ClientException("There was a problem with http request", e);
    } catch (IOException e) {
      throw new ClientException("There was a problem with http request", e);
    }

  }

  /**
   * Class uses castor's unmarshal method which is not compatibile with Java 1.5
   * therefore must supress unchecked warning
   * 
   * @param <T>
   * @param document
   * @param clazz
   * @return
   * @throws ClientException
   */
  @SuppressWarnings("unchecked")
  private <T extends ApiResponse> T unmarshal(Document document, Class<T> clazz) throws ClientException {
    Unmarshaller unmarshaller = new Unmarshaller(clazz);
    try {
      unmarshaller.setMapping(mapping);
    } catch (MappingException e1) {
      e1.printStackTrace();
    }
    unmarshaller.setIgnoreExtraElements(true);
    unmarshaller.setIgnoreExtraAttributes(true);
    try {
      return (T) unmarshaller.unmarshal(document.getFirstChild());
    } catch (MarshalException e) {
      throw new ClientException("There was a problem marshaling XML response", e);
    } catch (ValidationException e) {
      throw new ClientException("Invalid XML response returned by Viddler API", e);
    }
  }

  /**
   * Construct NameValuePair list for API get request (helper method)
   * 
   * @param vals
   * @return
   */
  private List<NameValuePair> nvps(String[][] vals) {
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    if (vals != null) {
      for (String[] pair : vals) {
        nvps.add(new NameValuePair(pair[0], pair[1]));
      }
    }
    return nvps;
  }

  /**
   * Is string empty or null?
   * 
   * @param value
   * @return
   */
  private boolean isBlank(String value) {
    return value == null || value.trim().length() == 0;
  }

  private String serializeTags(String[] tags) {
    StringBuffer serializedTags = new StringBuffer();
    if (tags != null) {
      for (String tag : tags) {
        if (serializedTags.length() > 0) {
          serializedTags.append(",");
        }
        serializedTags.append("\"").append(tag.replaceAll("\"", "")).append("\"");
      }
    }
    return serializedTags.toString();
  }

  public void setReauthenticate(boolean reauthenticate) {
    this.reauthenticate = reauthenticate;
  }

  public boolean isReauthenticate() {
    return reauthenticate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format(ViddlerApiClient.class.getName() + " [endpoint=%s, apiKey=%s, credentials=%s]", endpoint,
        apiKey, credentials != null ? credentials.toString() : null);
  }

}
