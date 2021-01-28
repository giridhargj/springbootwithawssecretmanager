package com.example.springbootwithawssecretmanager;

import java.io.IOException;
import java.util.Properties;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.InvalidParameterException;
import com.amazonaws.services.secretsmanager.model.InvalidRequestException;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

public class DBCredsListner implements ApplicationListener<ApplicationPreparedEvent> 
 {
    @Override
	public void onApplicationEvent(ApplicationPreparedEvent  event) {
        ConfigurableEnvironment env = event.getApplicationContext().getEnvironment();

        String secretName = env.getProperty("spring.aws.secretsmanager.secretName");
        String endPoint =  env.getProperty("spring.aws.secretsmanager.endpoint");
        String region = env.getProperty("spring.aws.secretsmanager.region");
       
        JsonNode secretsJson = getDBSecret(secretName, endPoint, region);

        String  username  =  secretsJson.get("username").textValue();
        String  password  =  secretsJson.get("password").textValue();

		Properties props = new Properties();
		props.put("spring.datasource.username", username);
		props.put("spring.datasource.password", password);
		env.getPropertySources().addFirst(new PropertiesPropertySource("aws.secret.manager", props));
		
    }
    
    private JsonNode getDBSecret(String secretName, String endPoints, String awsRegion) 
    {
       
        AwsClientBuilder.EndpointConfiguration  config  =  new  AwsClientBuilder.EndpointConfiguration(endPoints, awsRegion);
        AWSSecretsManagerClientBuilder  clientBuilder  =  AWSSecretsManagerClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(config);
        AWSSecretsManager  client  =  clientBuilder.build(); 

		ObjectMapper  objectMapper  =  new  ObjectMapper();
 
        JsonNode  secretsJson  =  null;        
       
        GetSecretValueRequest  getSecretValueRequest  =  new  GetSecretValueRequest().withSecretId(secretName); 
        
        GetSecretValueResult  getSecretValueResponse  =  null;
        
        try 
        {
                getSecretValueResponse  =  client.getSecretValue(getSecretValueRequest);
        }
        
        catch  (ResourceNotFoundException  e) 
        {
                System.out.println("The requested secret "  +  secretName  +  " was not found");
        }   
        
        catch  (InvalidRequestException  e) 
        {    
                System.out.println("The request was invalid due to: "  +  e.getMessage());
        }   
        
        catch  (InvalidParameterException  e)
        {    
                System.out.println("The request had invalid params: "  +  e.getMessage());
        }
        if  (getSecretValueResponse  ==  null)
        {    
            return  null;
        }  
       
 
        String secret = getSecretValueResponse.getSecretString();  
        
        if (secret != null) 
        {  
            try
            {        
                secretsJson  =  objectMapper.readTree(secret);    
            }   
        
            catch  (IOException  e) 
            {        
                System.out.println("Exception while retrieving secret values: "  +  e.getMessage());    
            }
        }   
     return secretsJson;
    }


}