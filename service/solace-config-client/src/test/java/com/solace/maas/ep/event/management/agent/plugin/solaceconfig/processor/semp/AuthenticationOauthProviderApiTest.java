/*
 * SEMP (Solace Element Management Protocol)
 * SEMP (starting in `v2`, see note 1) is a RESTful API for configuring, monitoring, and administering a Solace PubSub+ broker.  SEMP uses URIs to address manageable **resources** of the Solace PubSub+ broker. Resources are individual **objects**, **collections** of objects, or (exclusively in the action API) **actions**. This document applies to the following API:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Configuration|/SEMP/v2/config|Reading and writing config state|See note 2    The following APIs are also available:   API|Base Path|Purpose|Comments :---|:---|:---|:--- Action|/SEMP/v2/action|Performing actions|See note 2 Monitoring|/SEMP/v2/monitor|Querying operational parameters|See note 2    Resources are always nouns, with individual objects being singular and collections being plural.  Objects within a collection are identified by an `obj-id`, which follows the collection name with the form `collection-name/obj-id`.  Actions within an object are identified by an `action-id`, which follows the object name with the form `obj-id/action-id`.  Some examples:  ``` /SEMP/v2/config/msgVpns                        ; MsgVpn collection /SEMP/v2/config/msgVpns/a                      ; MsgVpn object named \"a\" /SEMP/v2/config/msgVpns/a/queues               ; Queue collection in MsgVpn \"a\" /SEMP/v2/config/msgVpns/a/queues/b             ; Queue object named \"b\" in MsgVpn \"a\" /SEMP/v2/action/msgVpns/a/queues/b/startReplay ; Action that starts a replay on Queue \"b\" in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients             ; Client collection in MsgVpn \"a\" /SEMP/v2/monitor/msgVpns/a/clients/c           ; Client object named \"c\" in MsgVpn \"a\" ```  ## Collection Resources  Collections are unordered lists of objects (unless described as otherwise), and are described by JSON arrays. Each item in the array represents an object in the same manner as the individual object would normally be represented. In the configuration API, the creation of a new object is done through its collection resource.  ## Object and Action Resources  Objects are composed of attributes, actions, collections, and other objects. They are described by JSON objects as name/value pairs. The collections and actions of an object are not contained directly in the object's JSON content; rather the content includes an attribute containing a URI which points to the collections and actions. These contained resources must be managed through this URI. At a minimum, every object has one or more identifying attributes, and its own `uri` attribute which contains the URI pointing to itself.  Actions are also composed of attributes, and are described by JSON objects as name/value pairs. Unlike objects, however, they are not members of a collection and cannot be retrieved, only performed. Actions only exist in the action API.  Attributes in an object or action may have any combination of the following properties:   Property|Meaning|Comments :---|:---|:--- Identifying|Attribute is involved in unique identification of the object, and appears in its URI| Const|Attribute value can only be chosen during object creation| Required|Attribute must be provided in the request| Read-Only|Attribute can only be read, not written.|See note 3 Write-Only|Attribute can only be written, not read, unless the attribute is also opaque|See the documentation for the opaque property Requires-Disable|Attribute can only be changed when object is disabled| Deprecated|Attribute is deprecated, and will disappear in the next SEMP version| Opaque|Attribute can be set or retrieved in opaque form when the `opaquePassword` query parameter is present|See the `opaquePassword` query parameter documentation    In some requests, certain attributes may only be provided in certain combinations with other attributes:   Relationship|Meaning :---|:--- Requires|Attribute may only be changed by a request if a particular attribute or combination of attributes is also provided in the request Conflicts|Attribute may only be provided in a request if a particular attribute or combination of attributes is not also provided in the request    In the monitoring API, any non-identifying attribute may not be returned in a GET.  ## HTTP Methods  The following HTTP methods manipulate resources in accordance with these general principles. Note that some methods are only used in certain APIs:   Method|Resource|Meaning|Request Body|Response Body|Notes :---|:---|:---|:---|:---|:--- POST|Collection|Create object|Initial attribute values|Object attributes and metadata|Absent attributes are set to default. If object already exists, a 400 error is returned PUT|Object|Update object|New attribute values|Object attributes and metadata|If does not exist, the object is first created. Absent attributes are set to default, with certain exceptions (see note 4) PUT|Action|Performs action|Action arguments|Action metadata| PATCH|Object|Update object|New attribute values|Object attributes and metadata|Absent attributes are left unchanged. If the object does not exist, a 404 error is returned DELETE|Object|Delete object|Empty|Object metadata|If the object does not exist, a 404 is returned GET|Object|Get object|Empty|Object attributes and metadata|If the object does not exist, a 404 is returned GET|Collection|Get collection|Empty|Object attributes and collection metadata|If the collection is empty, then an empty collection is returned with a 200 code    ## Common Query Parameters  The following are some common query parameters that are supported by many method/URI combinations. Individual URIs may document additional parameters. Note that multiple query parameters can be used together in a single URI, separated by the ampersand character. For example:  ``` ; Request for the MsgVpns collection using two hypothetical query parameters ; \"q1\" and \"q2\" with values \"val1\" and \"val2\" respectively /SEMP/v2/config/msgVpns?q1=val1&q2=val2 ```  ### select  Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. Use this query parameter to limit the size of the returned data for each returned object, return only those fields that are desired, or exclude fields that are not desired.  The value of `select` is a comma-separated list of attribute names. If the list contains attribute names that are not prefaced by `-`, only those attributes are included in the response. If the list contains attribute names that are prefaced by `-`, those attributes are excluded from the response. If the list contains both types, then the difference of the first set of attributes and the second set of attributes is returned. If the list is empty (i.e. `select=`), it is treated the same as if no `select` was provided: all attribute are returned.  All attributes that are prefaced by `-` must follow all attributes that are not prefaced by `-`. In addition, each attribute name in the list must match at least one attribute in the object.  Names may include the `*` wildcard (zero or more characters). Nested attribute names are supported using periods (e.g. `parentName.childName`).  Some examples:  ``` ; List of all MsgVpn names /SEMP/v2/config/msgVpns?select=msgVpnName ; List of all MsgVpn and their attributes except for their names /SEMP/v2/config/msgVpns?select=-msgVpnName ; Authentication attributes of MsgVpn \"finance\" /SEMP/v2/config/msgVpns/finance?select=authentication* ; All attributes of MsgVpn \"finance\" except for authentication attributes /SEMP/v2/config/msgVpns/finance?select=-authentication* ; Access related attributes of Queue \"orderQ\" of MsgVpn \"finance\" /SEMP/v2/config/msgVpns/finance/queues/orderQ?select=owner,permission ```  ### where  Include in the response only objects where certain conditions are true. Use this query parameter to limit which objects are returned to those whose attribute values meet the given conditions.  The value of `where` is a comma-separated list of expressions. All expressions must be true for the object to be included in the response. Each expression takes the form:  ``` expression  = attribute-name OP value OP          = '==' | '!=' | '&lt;' | '&gt;' | '&lt;=' | '&gt;=' ```  `value` may be a number, string, `true`, or `false`, as appropriate for the type of `attribute-name`. Greater-than and less-than comparisons only work for numbers. A `*` in a string `value` is interpreted as a wildcard (zero or more characters). Some examples:  ``` ; Only enabled MsgVpns /SEMP/v2/config/msgVpns?where=enabled==true ; Only MsgVpns using basic non-LDAP authentication /SEMP/v2/config/msgVpns?where=authenticationBasicEnabled==true,authenticationBasicType!=ldap ; Only MsgVpns that allow more than 100 client connections /SEMP/v2/config/msgVpns?where=maxConnectionCount>100 ; Only MsgVpns with msgVpnName starting with \"B\": /SEMP/v2/config/msgVpns?where=msgVpnName==B* ```  ### count  Limit the count of objects in the response. This can be useful to limit the size of the response for large collections. The minimum value for `count` is `1` and the default is `10`. There is also a per-collection maximum value to limit request handling time.  `count` does not guarantee that a minimum number of objects will be returned. A page may contain fewer than `count` objects or even be empty. Additional objects may nonetheless be available for retrieval on subsequent pages. See the `cursor` query parameter documentation for more information on paging.  For example: ``` ; Up to 25 MsgVpns /SEMP/v2/config/msgVpns?count=25 ```  ### cursor  The cursor, or position, for the next page of objects. Cursors are opaque data that should not be created or interpreted by SEMP clients, and should only be used as described below.  When a request is made for a collection and there may be additional objects available for retrieval that are not included in the initial response, the response will include a `cursorQuery` field containing a cursor. The value of this field can be specified in the `cursor` query parameter of a subsequent request to retrieve the next page of objects.  Applications must continue to use the `cursorQuery` if one is provided in order to retrieve the full set of objects associated with the request, even if a page contains fewer than the requested number of objects (see the `count` query parameter documentation) or is empty.  ### opaquePassword  Attributes with the opaque property are also write-only and so cannot normally be retrieved in a GET. However, when a password is provided in the `opaquePassword` query parameter, attributes with the opaque property are retrieved in a GET in opaque form, encrypted with this password. The query parameter can also be used on a POST, PATCH, or PUT to set opaque attributes using opaque attribute values retrieved in a GET, so long as:  1. the same password that was used to retrieve the opaque attribute values is provided; and  2. the broker to which the request is being sent has the same major and minor SEMP version as the broker that produced the opaque attribute values.  The password provided in the query parameter must be a minimum of 8 characters and a maximum of 128 characters.  The query parameter can only be used in the configuration API, and only over HTTPS.  ## Authentication  When a client makes its first SEMPv2 request, it must supply a username and password using HTTP Basic authentication, or an OAuth token or tokens using HTTP Bearer authentication.  When HTTP Basic authentication is used, the broker returns a cookie containing a session key. The client can omit the username and password from subsequent requests, because the broker can use the session cookie for authentication instead. When the session expires or is deleted, the client must provide the username and password again, and the broker creates a new session.  There are a limited number of session slots available on the broker. The broker returns 529 No SEMP Session Available if it is not able to allocate a session.  If certain attributes—such as a user's password—are changed, the broker automatically deletes the affected sessions. These attributes are documented below. However, changes in external user configuration data stored on a RADIUS or LDAP server do not trigger the broker to delete the associated session(s), therefore you must do this manually, if required.  A client can retrieve its current session information using the /about/user endpoint and delete its own session using the /about/user/logout endpoint. A client with appropriate permissions can also manage all sessions using the /sessions endpoint.  Sessions are not created when authenticating with an OAuth token or tokens using HTTP Bearer authentication. If a session cookie is provided, it is ignored.  ## Help  Visit [our website](https://solace.com) to learn more about Solace.  You can also download the SEMP API specifications by clicking [here](https://solace.com/downloads/).  If you need additional support, please contact us at [support@solace.com](mailto:support@solace.com).  ## Notes  Note|Description :---:|:--- 1|This specification defines SEMP starting in \"v2\", and not the original SEMP \"v1\" interface. Request and response formats between \"v1\" and \"v2\" are entirely incompatible, although both protocols share a common port configuration on the Solace PubSub+ broker. They are differentiated by the initial portion of the URI path, one of either \"/SEMP/\" or \"/SEMP/v2/\" 2|This API is partially implemented. Only a subset of all objects are available. 3|Read-only attributes may appear in POST and PUT/PATCH requests. However, if a read-only attribute is not marked as identifying, it will be ignored during a PUT/PATCH. 4|On a PUT, if the SEMP user is not authorized to modify the attribute, its value is left unchanged rather than set to default. In addition, the values of write-only attributes are not set to their defaults on a PUT, except in the following two cases: there is a mutual requires relationship with another non-write-only attribute, both attributes are absent from the request, and the non-write-only attribute is not currently set to its default value; or the attribute is also opaque and the `opaquePassword` query parameter is provided in the request.  
 *
 * The version of the OpenAPI document: 2.28
 * Contact: support@solace.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp;

import com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp.model.MsgVpnAuthenticationOauthProvider;
import com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp.model.MsgVpnAuthenticationOauthProviderResponse;
import com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp.model.MsgVpnAuthenticationOauthProvidersResponse;
import com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp.model.SempMetaOnlyResponse;
import org.junit.Test;
import org.junit.Ignore;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AuthenticationOauthProviderApi
 */
@Ignore
public class AuthenticationOauthProviderApiTest {

    private final AuthenticationOauthProviderApi api = new AuthenticationOauthProviderApi();

    
    /**
     * Create an OAuth Provider object.
     *
     * Create an OAuth Provider object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates and replication sites via config-sync.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: audienceClaimName|||||x| audienceClaimSource|||||x| audienceClaimValue|||||x| audienceValidationEnabled|||||x| authorizationGroupClaimName|||||x| authorizationGroupClaimSource|||||x| authorizationGroupEnabled|||||x| disconnectOnTokenExpirationEnabled|||||x| enabled|||||x| jwksRefreshInterval|||||x| jwksUri|||||x| msgVpnName|x||x||x| oauthProviderName|x|x|||x| tokenIgnoreTimeLimitsEnabled|||||x| tokenIntrospectionParameterName|||||x| tokenIntrospectionPassword||||x|x|x tokenIntrospectionTimeout|||||x| tokenIntrospectionUri|||||x| tokenIntrospectionUsername|||||x| usernameClaimName|||||x| usernameClaimSource|||||x| usernameValidateEnabled|||||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.25. Replaced by authenticationOauthProfiles.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createMsgVpnAuthenticationOauthProviderTest() {
        String msgVpnName = null;
        MsgVpnAuthenticationOauthProvider body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProviderResponse response = api.createMsgVpnAuthenticationOauthProvider(msgVpnName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Delete an OAuth Provider object.
     *
     * Delete an OAuth Provider object. The deletion of instances of this object are synchronized to HA mates and replication sites via config-sync.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.  A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.25. Replaced by authenticationOauthProfiles.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteMsgVpnAuthenticationOauthProviderTest() {
        String msgVpnName = null;
        String oauthProviderName = null;
        SempMetaOnlyResponse response = api.deleteMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName);

        // TODO: test validations
    }
    
    /**
     * Get an OAuth Provider object.
     *
     * Get an OAuth Provider object.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: audienceClaimName|||x| audienceClaimSource|||x| audienceClaimValue|||x| audienceValidationEnabled|||x| authorizationGroupClaimName|||x| authorizationGroupClaimSource|||x| authorizationGroupEnabled|||x| disconnectOnTokenExpirationEnabled|||x| enabled|||x| jwksRefreshInterval|||x| jwksUri|||x| msgVpnName|x||x| oauthProviderName|x||x| tokenIgnoreTimeLimitsEnabled|||x| tokenIntrospectionParameterName|||x| tokenIntrospectionPassword||x|x|x tokenIntrospectionTimeout|||x| tokenIntrospectionUri|||x| tokenIntrospectionUsername|||x| usernameClaimName|||x| usernameClaimSource|||x| usernameValidateEnabled|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.25. Replaced by authenticationOauthProfiles.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthenticationOauthProviderTest() {
        String msgVpnName = null;
        String oauthProviderName = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProviderResponse response = api.getMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Get a list of OAuth Provider objects.
     *
     * Get a list of OAuth Provider objects.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: audienceClaimName|||x| audienceClaimSource|||x| audienceClaimValue|||x| audienceValidationEnabled|||x| authorizationGroupClaimName|||x| authorizationGroupClaimSource|||x| authorizationGroupEnabled|||x| disconnectOnTokenExpirationEnabled|||x| enabled|||x| jwksRefreshInterval|||x| jwksUri|||x| msgVpnName|x||x| oauthProviderName|x||x| tokenIgnoreTimeLimitsEnabled|||x| tokenIntrospectionParameterName|||x| tokenIntrospectionPassword||x|x|x tokenIntrospectionTimeout|||x| tokenIntrospectionUri|||x| tokenIntrospectionUsername|||x| usernameClaimName|||x| usernameClaimSource|||x| usernameValidateEnabled|||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-only\&quot; is required to perform this operation.  This has been deprecated since 2.25. Replaced by authenticationOauthProfiles.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getMsgVpnAuthenticationOauthProvidersTest() {
        String msgVpnName = null;
        Integer count = null;
        String cursor = null;
        String opaquePassword = null;
        List<String> where = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProvidersResponse response = api.getMsgVpnAuthenticationOauthProviders(msgVpnName, count, cursor, opaquePassword, where, select);

        // TODO: test validations
    }
    
    /**
     * Replace an OAuth Provider object.
     *
     * Replace an OAuth Provider object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Const|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---|:---|:---|:---|:---|:---|:--- audienceClaimName||||||x| audienceClaimSource||||||x| audienceClaimValue||||||x| audienceValidationEnabled||||||x| authorizationGroupClaimName||||||x| authorizationGroupClaimSource||||||x| authorizationGroupEnabled||||||x| disconnectOnTokenExpirationEnabled||||||x| enabled||||||x| jwksRefreshInterval||||||x| jwksUri||||||x| msgVpnName|x||x|||x| oauthProviderName|x||x|||x| tokenIgnoreTimeLimitsEnabled||||||x| tokenIntrospectionParameterName||||||x| tokenIntrospectionPassword||||x||x|x tokenIntrospectionTimeout||||||x| tokenIntrospectionUri||||||x| tokenIntrospectionUsername||||||x| usernameClaimName||||||x| usernameClaimSource||||||x| usernameValidateEnabled||||||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.25. Replaced by authenticationOauthProfiles.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void replaceMsgVpnAuthenticationOauthProviderTest() {
        String msgVpnName = null;
        String oauthProviderName = null;
        MsgVpnAuthenticationOauthProvider body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProviderResponse response = api.replaceMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName, body, opaquePassword, select);

        // TODO: test validations
    }
    
    /**
     * Update an OAuth Provider object.
     *
     * Update an OAuth Provider object. Any attribute missing from the request will be left unchanged.  OAuth Providers contain information about the issuer of an OAuth token that is needed to validate the token and derive a client username from it.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---|:---|:---|:---|:---|:--- audienceClaimName|||||x| audienceClaimSource|||||x| audienceClaimValue|||||x| audienceValidationEnabled|||||x| authorizationGroupClaimName|||||x| authorizationGroupClaimSource|||||x| authorizationGroupEnabled|||||x| disconnectOnTokenExpirationEnabled|||||x| enabled|||||x| jwksRefreshInterval|||||x| jwksUri|||||x| msgVpnName|x|x|||x| oauthProviderName|x|x|||x| tokenIgnoreTimeLimitsEnabled|||||x| tokenIntrospectionParameterName|||||x| tokenIntrospectionPassword|||x||x|x tokenIntrospectionTimeout|||||x| tokenIntrospectionUri|||||x| tokenIntrospectionUsername|||||x| usernameClaimName|||||x| usernameClaimSource|||||x| usernameValidateEnabled|||||x|    A SEMP client authorized with a minimum access scope/level of \&quot;vpn/read-write\&quot; is required to perform this operation.  This has been deprecated since 2.25. Replaced by authenticationOauthProfiles.
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void updateMsgVpnAuthenticationOauthProviderTest() {
        String msgVpnName = null;
        String oauthProviderName = null;
        MsgVpnAuthenticationOauthProvider body = null;
        String opaquePassword = null;
        List<String> select = null;
        MsgVpnAuthenticationOauthProviderResponse response = api.updateMsgVpnAuthenticationOauthProvider(msgVpnName, oauthProviderName, body, opaquePassword, select);

        // TODO: test validations
    }
    
}
