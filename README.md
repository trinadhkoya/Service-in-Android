**`_# Service-in-Android_`**



`_in this while working with Service and Intent Service,There is nothing we can do with UI ,As in the name it self suggests it servers by working in the Background_
`

**Agenda is :**

`Trigger a Notification for each 60sec(1 Min) using Alarm Service with a Notification and Sound as Well
`

`How do we get start our Service ,it's Quite simple.Create a Service class which Extends IntentService
and make sure it should be present in Manifest `

you can see here how did i mentioned

        <service
            android:name=".MyIntentService"
            android:exported="false"></service>



We can start our services when a perticular action want to be triggered
like ,Wake me up at 6'0 clock daily in the Morning or send me a notification evry minute like

    MyIntentService.startActionFoo(getApplicationContext(),true,null);


MyIntentService is my Service name   and i want to  perform some action like sending a  notification .so i created a method
startActionFoo().in side i  wrote my logic to send a notification every min


**REMEMBER:**

`In order to run your Service,You must have working Internet connection
`


**Why IntentService and Why not Servce ?**


`You have to create a worker Thread in Service(The Service runs in background but it runs on the Main Thread of the application.
)
`


`If you implement a Service, it is your responsibility to stop the service when its work is done, by calling stopSelf() or stopService(). (If you only want to provide binding, you don't need to implement this method).
`

`where as in IntentService it automatically created and stops itself once it get done
`


`SPECIAL THANKS TO ANDROID TUTORIALS POINT:
`



**AUTHOR**:

**`TRINADH KOYA**
`