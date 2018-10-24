package com.accenture.dcsc.adp.portal.api.tracking.web;

import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

/*
  {
    "client_id": "7805.adp.portal.web",
    "scope": [
      "user_profile",
      "group_userprofile"
    ],
    "sub": "shyamsundar.eg@example.com",
    "amr": "external",
    "auth_time": 1111111111,
    "idp": "eso_mfa",
    "upn": "shyamsundar.eg@example.com",
    "email": "shyamsundar.eg@example.com",
    "samaccount_name": "shyamsundar.eg",
    "peoplekey": "12345678",
    "personnelnbr": "12345678",
    "given_name": "Rob",
    "sn": "Doe",
    "group": [
      "adp.project1.developers"
    ],
    "iss": "urn:federation:mycompany:stage",
    "aud": "urn:federation:mycompany:stage/resources"
  }
*/
public class shyamsundar {

    public static RequestPostProcessor token() {
        return new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.addHeader(HttpHeaders.AUTHORIZATION, bearerToken());
                return request;
            }
        };
    }

    public static HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, bearerToken());
        return headers;
    }

    private static String bearerToken() {
        return "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ind0NFVKb0lheHFadUpSTnE5Sk12LW5LU0FQOCIsImtpZCI6Ind0NFVKb0lheHFadUpSTnE5Sk12LW5LU0FQOCJ9.eyJjbGllbnRfaWQiOiI3ODA1LmFkb3AucG9ydGFsLndlYiIsInNjb3BlIjpbInVzZXJfcHJvZmlsZSIsImdyb3VwX3VzZXJwcm9maWxlIl0sInN1YiI6InJvYi5kb2VAZXhhbXBsZS5jb20iLCJhbXIiOiJleHRlcm5hbCIsImF1dGhfdGltZSI6MTExMTExMTExMSwiaWRwIjoiZXNvX21mYSIsInVwbiI6InJvYi5kb2VAZXhhbXBsZS5jb20iLCJlbWFpbCI6InJvYi5kb2VAZXhhbXBsZS5jb20iLCJzYW1hY2NvdW50X25hbWUiOiJyb2IuZG9lIiwicGVvcGxla2V5IjoiMTIzNDU2NzgiLCJwZXJzb25uZWxuYnIiOiIxMjM0NTY3OCIsImdpdmVuX25hbWUiOiJSb2IiLCJzbiI6IkRvZSIsImdyb3VwIjpbImFkb3AucHJvamVjdDEuZGV2ZWxvcGVycyJdLCJpc3MiOiJ1cm46ZmVkZXJhdGlvbjpteWNvbXBhbnk6c3RhZ2UiLCJhdWQiOiJ1cm46ZmVkZXJhdGlvbjpteWNvbXBhbnk6c3RhZ2UvcmVzb3VyY2VzIn0.LRoam1kgC_bM5v8XC61YG-LSv2vb9W3d-PB-1vhtxmy5ldDbcJtCiLqY9BtKktmF0Pn1rZm1auilsghv83Ncimh8ECMGKCYetpZhfRRqu0MV1yKZ4kN3kH9DUwasLKWVDY5xr1vlkRqea0EzUlte231ISvkbdzQmqJTsN7iV5gqDaMQO5IgVfVeCiebq10dcSoTexAfnNrQdAgBkXvhNiXPFo_AGmCfntAMxCRAMyS2iL_Yqb8FLPpjZWOlR7Zt6zxbDlwGytHXP0KIfHFDGb2sTE8D_2TJ9BSnIoScS_RSExAp1I5fcL-SIfn8OLg30K7F400MtURUaOOKNc8figw";
    }
}
