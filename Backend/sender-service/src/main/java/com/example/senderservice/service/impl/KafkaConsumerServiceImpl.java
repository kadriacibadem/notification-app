package com.example.senderservice.service.impl;

//@Service
//@RequiredArgsConstructor
//public class KafkaConsumerServiceImpl {
//
//    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;
//
//    @KafkaListener(topics = "notification", groupId = "receive-group", containerFactory = "kafkaListenerContainerFactory")
//     public void getMessageFromKafka(@Payload  MessageDTO messageDTO) {
//        if(messageDTO.getFromTime().getMinute() == LocalTime.now().getMinute()){
//            System.out.println("Message received: " + messageDTO);
//        }else{
//            kafkaTemplate.send("notification", messageDTO);
//        }
//    }
//}
