import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.util.*;

public class TestSqs {

    public static void main(String[] args)  {

        AWSCredentials credentials = new BasicAWSCredentials(
                "Your AccessKey",
                "Your SecretKey"
        );

        AmazonSQS sqs = AmazonSQSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_NORTHEAST_1)
                .build();

        CreateQueueRequest createStandardQueueRequest = new CreateQueueRequest("sqs-first");
        String standardQueueUrl = sqs.createQueue(createStandardQueueRequest).getQueueUrl();

        List<String> messagelist = new ArrayList<>();

        boolean flag = true;

        while (flag) {

            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                    .withQueueUrl(standardQueueUrl)
                    .withWaitTimeSeconds(2)
                    .withMaxNumberOfMessages(10);
            List<Message> sqsMessages = sqs.receiveMessage(receiveMessageRequest).getMessages();

            for (Message msg : sqsMessages) {

//                System.out.println("body : " + msg.getBody());
                messagelist.add(msg.getBody());

                String messageReceiptHandle = msg.getReceiptHandle();

                // 폴링한 것에 대해서 레시트를 이용하여 Queue에서 삭제
                System.out.println(messageReceiptHandle);
                sqs.deleteMessage(new DeleteMessageRequest().withQueueUrl(standardQueueUrl).withReceiptHandle(messageReceiptHandle));
            }

            System.out.println("msg size : " + sqsMessages.size());
            if (sqsMessages.size() == 0) {
                flag = false;
            }
        }
        System.out.println("Result Message List : " + messagelist);
    }
}
