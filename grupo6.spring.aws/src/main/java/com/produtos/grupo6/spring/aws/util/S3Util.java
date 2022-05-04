package com.produtos.grupo6.spring.aws.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3Util {
        private static final String BUCKET = "grupo6-bucket";

        public static URI uploadFile(String fileName, InputStream inputStream)
                        throws S3Exception, AwsServiceException, SdkClientException, IOException, URISyntaxException {
                S3Client client = S3Client.builder().build();

                PutObjectRequest request = PutObjectRequest.builder()
                                .bucket(BUCKET)
                                .key(fileName)
                                .build();

                client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
                GetUrlRequest requestUrl = GetUrlRequest.builder().bucket(BUCKET).key(fileName).build();
                URI url = client.utilities().getUrl(requestUrl).toURI();

                return url;
        }
}
