package com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp;

import com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp.invoker.ApiClient;

import com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp.model.DomainCertAuthoritiesResponse;
import com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp.model.DomainCertAuthority;
import com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp.model.DomainCertAuthorityResponse;
import com.solace.maas.ep.event.management.agent.plugin.solaceconfig.processor.semp.model.SempMetaOnlyResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-05-17T23:49:01.929728+01:00[Europe/London]")
public class DomainCertAuthorityApi {
    private ApiClient apiClient;

    public DomainCertAuthorityApi() {
        this(new ApiClient());
    }

    public DomainCertAuthorityApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Create a Domain Certificate Authority object.
     * Create a Domain Certificate Authority object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The Domain Certificate Authority object&#39;s attributes after being created, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param body The Domain Certificate Authority object&#39;s attributes. (required)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return DomainCertAuthorityResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DomainCertAuthorityResponse createDomainCertAuthority(DomainCertAuthority body, String opaquePassword, List<String> select) throws RestClientException {
        return createDomainCertAuthorityWithHttpInfo(body, opaquePassword, select).getBody();
    }

    /**
     * Create a Domain Certificate Authority object.
     * Create a Domain Certificate Authority object. Any attribute missing from the request will be set to its default value. The creation of instances of this object are synchronized to HA mates via config-sync.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Required|Read-Only|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---:|:---:|:---: certAuthorityName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The Domain Certificate Authority object&#39;s attributes after being created, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param body The Domain Certificate Authority object&#39;s attributes. (required)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return ResponseEntity&lt;DomainCertAuthorityResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DomainCertAuthorityResponse> createDomainCertAuthorityWithHttpInfo(DomainCertAuthority body, String opaquePassword, List<String> select) throws RestClientException {
        Object localVarPostBody = body;
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling createDomainCertAuthority");
        }
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "opaquePassword", opaquePassword));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "select", select));

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<DomainCertAuthorityResponse> localReturnType = new ParameterizedTypeReference<DomainCertAuthorityResponse>() {};
        return apiClient.invokeAPI("/domainCertAuthorities", HttpMethod.POST, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Delete a Domain Certificate Authority object.
     * Delete a Domain Certificate Authority object. The deletion of instances of this object are synchronized to HA mates via config-sync.  Certificate Authorities trusted for domain verification.  A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The request metadata.
     * <p><b>0</b> - The error response.
     * @param certAuthorityName The name of the Certificate Authority. (required)
     * @return SempMetaOnlyResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SempMetaOnlyResponse deleteDomainCertAuthority(String certAuthorityName) throws RestClientException {
        return deleteDomainCertAuthorityWithHttpInfo(certAuthorityName).getBody();
    }

    /**
     * Delete a Domain Certificate Authority object.
     * Delete a Domain Certificate Authority object. The deletion of instances of this object are synchronized to HA mates via config-sync.  Certificate Authorities trusted for domain verification.  A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The request metadata.
     * <p><b>0</b> - The error response.
     * @param certAuthorityName The name of the Certificate Authority. (required)
     * @return ResponseEntity&lt;SempMetaOnlyResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SempMetaOnlyResponse> deleteDomainCertAuthorityWithHttpInfo(String certAuthorityName) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'certAuthorityName' is set
        if (certAuthorityName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'certAuthorityName' when calling deleteDomainCertAuthority");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("certAuthorityName", certAuthorityName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<SempMetaOnlyResponse> localReturnType = new ParameterizedTypeReference<SempMetaOnlyResponse>() {};
        return apiClient.invokeAPI("/domainCertAuthorities/{certAuthorityName}", HttpMethod.DELETE, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Get a list of Domain Certificate Authority objects.
     * Get a list of Domain Certificate Authority objects.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The list of Domain Certificate Authority objects&#39; attributes, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param count Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. (optional, default to 10)
     * @param cursor The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. (optional)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param where Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return DomainCertAuthoritiesResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DomainCertAuthoritiesResponse getDomainCertAuthorities(Integer count, String cursor, String opaquePassword, List<String> where, List<String> select) throws RestClientException {
        return getDomainCertAuthoritiesWithHttpInfo(count, cursor, opaquePassword, where, select).getBody();
    }

    /**
     * Get a list of Domain Certificate Authority objects.
     * Get a list of Domain Certificate Authority objects.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The list of Domain Certificate Authority objects&#39; attributes, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param count Limit the count of objects in the response. See the documentation for the &#x60;count&#x60; parameter. (optional, default to 10)
     * @param cursor The cursor, or position, for the next page of objects. See the documentation for the &#x60;cursor&#x60; parameter. (optional)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param where Include in the response only objects where certain conditions are true. See the the documentation for the &#x60;where&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return ResponseEntity&lt;DomainCertAuthoritiesResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DomainCertAuthoritiesResponse> getDomainCertAuthoritiesWithHttpInfo(Integer count, String cursor, String opaquePassword, List<String> where, List<String> select) throws RestClientException {
        Object localVarPostBody = null;
        

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "count", count));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "cursor", cursor));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "opaquePassword", opaquePassword));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "where", where));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "select", select));

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<DomainCertAuthoritiesResponse> localReturnType = new ParameterizedTypeReference<DomainCertAuthoritiesResponse>() {};
        return apiClient.invokeAPI("/domainCertAuthorities", HttpMethod.GET, Collections.<String, Object>emptyMap(), localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Get a Domain Certificate Authority object.
     * Get a Domain Certificate Authority object.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The Domain Certificate Authority object&#39;s attributes, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param certAuthorityName The name of the Certificate Authority. (required)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return DomainCertAuthorityResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DomainCertAuthorityResponse getDomainCertAuthority(String certAuthorityName, String opaquePassword, List<String> select) throws RestClientException {
        return getDomainCertAuthorityWithHttpInfo(certAuthorityName, opaquePassword, select).getBody();
    }

    /**
     * Get a Domain Certificate Authority object.
     * Get a Domain Certificate Authority object.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Write-Only|Deprecated|Opaque :---|:---:|:---:|:---:|:---: certAuthorityName|x|||    A SEMP client authorized with a minimum access scope/level of \&quot;global/read-only\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The Domain Certificate Authority object&#39;s attributes, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param certAuthorityName The name of the Certificate Authority. (required)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return ResponseEntity&lt;DomainCertAuthorityResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DomainCertAuthorityResponse> getDomainCertAuthorityWithHttpInfo(String certAuthorityName, String opaquePassword, List<String> select) throws RestClientException {
        Object localVarPostBody = null;
        
        // verify the required parameter 'certAuthorityName' is set
        if (certAuthorityName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'certAuthorityName' when calling getDomainCertAuthority");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("certAuthorityName", certAuthorityName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "opaquePassword", opaquePassword));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "select", select));

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<DomainCertAuthorityResponse> localReturnType = new ParameterizedTypeReference<DomainCertAuthorityResponse>() {};
        return apiClient.invokeAPI("/domainCertAuthorities/{certAuthorityName}", HttpMethod.GET, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Replace a Domain Certificate Authority object.
     * Replace a Domain Certificate Authority object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Const|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---|:---|:---|:---|:---|:---|:--- certAuthorityName|x||x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The Domain Certificate Authority object&#39;s attributes after being replaced, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param certAuthorityName The name of the Certificate Authority. (required)
     * @param body The Domain Certificate Authority object&#39;s attributes. (required)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return DomainCertAuthorityResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DomainCertAuthorityResponse replaceDomainCertAuthority(String certAuthorityName, DomainCertAuthority body, String opaquePassword, List<String> select) throws RestClientException {
        return replaceDomainCertAuthorityWithHttpInfo(certAuthorityName, body, opaquePassword, select).getBody();
    }

    /**
     * Replace a Domain Certificate Authority object.
     * Replace a Domain Certificate Authority object. Any attribute missing from the request will be set to its default value, subject to the exceptions in note 4.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Const|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---|:---|:---|:---|:---|:---|:--- certAuthorityName|x||x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The Domain Certificate Authority object&#39;s attributes after being replaced, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param certAuthorityName The name of the Certificate Authority. (required)
     * @param body The Domain Certificate Authority object&#39;s attributes. (required)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return ResponseEntity&lt;DomainCertAuthorityResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DomainCertAuthorityResponse> replaceDomainCertAuthorityWithHttpInfo(String certAuthorityName, DomainCertAuthority body, String opaquePassword, List<String> select) throws RestClientException {
        Object localVarPostBody = body;
        
        // verify the required parameter 'certAuthorityName' is set
        if (certAuthorityName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'certAuthorityName' when calling replaceDomainCertAuthority");
        }
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling replaceDomainCertAuthority");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("certAuthorityName", certAuthorityName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "opaquePassword", opaquePassword));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "select", select));

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<DomainCertAuthorityResponse> localReturnType = new ParameterizedTypeReference<DomainCertAuthorityResponse>() {};
        return apiClient.invokeAPI("/domainCertAuthorities/{certAuthorityName}", HttpMethod.PUT, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
    /**
     * Update a Domain Certificate Authority object.
     * Update a Domain Certificate Authority object. Any attribute missing from the request will be left unchanged.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---|:---|:---|:---|:---|:--- certAuthorityName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The Domain Certificate Authority object&#39;s attributes after being updated, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param certAuthorityName The name of the Certificate Authority. (required)
     * @param body The Domain Certificate Authority object&#39;s attributes. (required)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return DomainCertAuthorityResponse
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public DomainCertAuthorityResponse updateDomainCertAuthority(String certAuthorityName, DomainCertAuthority body, String opaquePassword, List<String> select) throws RestClientException {
        return updateDomainCertAuthorityWithHttpInfo(certAuthorityName, body, opaquePassword, select).getBody();
    }

    /**
     * Update a Domain Certificate Authority object.
     * Update a Domain Certificate Authority object. Any attribute missing from the request will be left unchanged.  Certificate Authorities trusted for domain verification.   Attribute|Identifying|Read-Only|Write-Only|Requires-Disable|Deprecated|Opaque :---|:---|:---|:---|:---|:---|:--- certAuthorityName|x|x||||    A SEMP client authorized with a minimum access scope/level of \&quot;global/admin\&quot; is required to perform this operation.  This has been available since 2.19.
     * <p><b>200</b> - The Domain Certificate Authority object&#39;s attributes after being updated, and the request metadata.
     * <p><b>0</b> - The error response.
     * @param certAuthorityName The name of the Certificate Authority. (required)
     * @param body The Domain Certificate Authority object&#39;s attributes. (required)
     * @param opaquePassword Accept opaque attributes in the request or return opaque attributes in the response, encrypted with the specified password. See the documentation for the &#x60;opaquePassword&#x60; parameter. (optional)
     * @param select Include in the response only selected attributes of the object, or exclude from the response selected attributes of the object. See the documentation for the &#x60;select&#x60; parameter. (optional)
     * @return ResponseEntity&lt;DomainCertAuthorityResponse&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<DomainCertAuthorityResponse> updateDomainCertAuthorityWithHttpInfo(String certAuthorityName, DomainCertAuthority body, String opaquePassword, List<String> select) throws RestClientException {
        Object localVarPostBody = body;
        
        // verify the required parameter 'certAuthorityName' is set
        if (certAuthorityName == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'certAuthorityName' when calling updateDomainCertAuthority");
        }
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'body' when calling updateDomainCertAuthority");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("certAuthorityName", certAuthorityName);

        final MultiValueMap<String, String> localVarQueryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders localVarHeaderParams = new HttpHeaders();
        final MultiValueMap<String, String> localVarCookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> localVarFormParams = new LinkedMultiValueMap<String, Object>();

        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "opaquePassword", opaquePassword));
        localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)), "select", select));

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "basicAuth" };

        ParameterizedTypeReference<DomainCertAuthorityResponse> localReturnType = new ParameterizedTypeReference<DomainCertAuthorityResponse>() {};
        return apiClient.invokeAPI("/domainCertAuthorities/{certAuthorityName}", HttpMethod.PATCH, uriVariables, localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localReturnType);
    }
}
