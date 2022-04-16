# aws-sqs-all-message-polling
AWS SQS Queue Message All Polling

AWS SQS 서비스의 대기열에 메시지를 저장한 후 폴링을 할때,  
모든 건수가 폴링되지 않는 문제가 있어서 그부분을 어떻게 해결했는지 기록  

while문의 false : 대기열에 남아있는 메세지 건수가 0일때 

Point  
- while 조건이 false 가 될때까지 반복적으로 Queue 메세지를 받아와서 작업 리스트에 지속적으로 추가.
- 받은 Queue 메세지에 관해서는 Queue에서 삭제




