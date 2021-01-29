# springbootwithawssecretmanager
 
 
 1. Create a AWS Secret. Follow https://docs.aws.amazon.com/secretsmanager/latest/userguide/tutorials_basic.html 
 2. Download the project 
 3. Update application.properties as per Secret Name created above along with EndPoint and Region
       spring.aws.secretsmanager.secretName="your secret name"
       spring.aws.secretsmanager.endpoint="secret manager end point"
       spring.aws.secretsmanager.region="region"
       
 4.Run the Application
 
 5.Verify that spring boot app is connecting to DB.
 
