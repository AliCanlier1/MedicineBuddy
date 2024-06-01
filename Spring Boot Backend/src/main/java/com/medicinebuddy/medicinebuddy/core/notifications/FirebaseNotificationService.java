package com.medicinebuddy.medicinebuddy.core.notifications;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.medicinebuddy.medicinebuddy.entities.NotificationMessage;
import com.medicinebuddy.medicinebuddy.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class FirebaseNotificationService {

    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public FirebaseNotificationService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendNotificationtoPatient(NotificationMessage notificationMessage){

        Notification notification = Notification
                .builder()
                .setTitle("Medicine Reminder")
                .setBody("Take your pills!")
                .setImage("https://media.istockphoto.com/id/1300036753/tr/foto%C4%9Fraf/d%C3%BC%C5%9Fen-antibiyotikler-sa%C4%9Fl%C4%B1k-arka-plan.jpg?s=612x612&w=is&k=20&c=9gyO4So8xXiMCTDAh8khq-DgdsYUiOjeCq-lvoIqgvc=")
                .build();

        Message message = Message
                .builder()
                .setToken(notificationMessage.getToken())
                .setNotification(notification)
                .putData("takenMedicineIds", notificationMessage.getData().toString())
                .build();

        try{
            firebaseMessaging.send(message);
        }
        catch (FirebaseMessagingException ignored){
        }
    }

    public void sendNotificationtoBuddy(NotificationMessage notificationMessage, Set<Patient> patientList){

        String notificationBodyMessage="";

        if(patientList.size()>1){
            for(Patient patient : patientList){
                notificationBodyMessage += patient.getName()+" "+patient.getSurname()+", ";
            }
            notificationBodyMessage = notificationBodyMessage.substring(0, notificationBodyMessage.length()-2)+" have not taken his/her medicines.";
        }
        else{
            for(Patient patient : patientList){
                notificationBodyMessage += patient.getName()+" "+patient.getSurname()+" has not taken his/her medicines.";
            }

        }

        Notification notification = Notification
                .builder()
                .setTitle("Medicine Reminder")
                .setBody(notificationBodyMessage)
                .setImage("https://img.freepik.com/free-photo/yellow-triangle-warning-sign-symbol-danger-caution-risk-traffic-icon-background-3d-rendering_56104-1156.jpg?size=626&ext=jpg&ga=GA1.1.735520172.1712102400&semt=ais")
                .build();

        Message message = Message
                .builder()
                .setToken(notificationMessage.getToken())
                .setNotification(notification)
                .putData("takenMedicineIds", notificationMessage.getData().toString())
                .build();

        try{
            firebaseMessaging.send(message);
        }
        catch (FirebaseMessagingException ignored){
        }
    }
    public void sendCriticalNotificationToPatient(NotificationMessage notificationMessage){
        Notification notification = Notification
                .builder()
                .setTitle("You have not taken your important medicine!")
                .setBody("Medicine Name: "+ notificationMessage.getCriticalMedicineName()+ "\nDate and Time: "+ notificationMessage.getCriticalMedicineDateAndTime())
                .setImage("https://i.pinimg.com/736x/5d/bd/ba/5dbdbab451023062c1f32adf1349aa76.jpg")
                .build();

        Message message = Message
                .builder()
                .setToken(notificationMessage.getToken())
                .setNotification(notification)
                .build();

        try{
            firebaseMessaging.send(message);
        }
        catch (FirebaseMessagingException ignored){
        }
    }
    public void sendCriticalNotificationToBuddy(NotificationMessage notificationMessage, String patientName){

        Notification notification = Notification
                .builder()
                .setTitle(patientName+" has not taken his critical medicine!")
                .setBody("Medicine Name: "+ notificationMessage.getCriticalMedicineName()+ "\nDate and Time:"+ notificationMessage.getCriticalMedicineDateAndTime())
                .setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5IF0alRCTazVcQLQBiy2C4L7Q_AEmxQLlAxXoch8C9nUVgi8he5DQ09R599HcPiwBeTQ&usqp=CAU")
                .build();

        Message message = Message
                .builder()
                .setToken(notificationMessage.getToken())
                .setNotification(notification)
                .build();

        try{
            firebaseMessaging.send(message);
        }
        catch (FirebaseMessagingException ignored){
        }
    }
}
