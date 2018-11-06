/*
 SIMPLE GPIO
*/

int pushButton[3] = {5,6,7}; // where are the buttons connected PINs
int buttonState[3];          // what is the state of the button (1==PUSHED IN)

int oldbuttonState[3] = {0,0,0};  //this stores the previous state of the button (1==PUSHED IN)


int ledOut[4] = {8,9,10,11}; // where the LEDs are connected
int ledState[4] = {0,0,0,0}; //what is the state of the LEDs


//SETUP 

void setup() 
{
 
  Serial.begin(9600);

  //every button  GPIO input
  for(int i=0;i<3;i++)
    pinMode(pushButton[i], INPUT);

  //every LED GPIO-t output
  for(int i=0;i<4;i++)
    pinMode(ledOut[i], OUTPUT);
}

// LOOP 

void loop() 
{
  //read in all input pins
  for(int i=0;i<3;i++)
    buttonState[i] = digitalRead(pushButton[i]);

  for(int i=0;i<3;i++)
    if (buttonState[i] != oldbuttonState[i])
        {
         if (buttonState[i]==0) //switch at release
            //ledState[i] = !ledState[i];
            {
              switch(i)
               {
                case 0: {//add one
                        ledState[0]++;
                        for(int j=0;j<3;j++)
                          {
                           if (ledState[j]>=2)
                              {
                               ledState[j+1]++;
                               ledState[j]-=2; 
                              }
                          }
                         if (ledState[3]>=2) ledState[3]-=2;
                         break;}
                case 1: {//subtract
                        ledState[0]--;
                        for(int j=0;j<3;j++)
                          {
                           if (ledState[j]<0)
                              {
                               ledState[j+1]--;
                               ledState[j]+=2; 
                              }
                          }
                         if (ledState[3]<0) ledState[3]+=2;
                  
                  
                         break;}
                case 2: {//shift left
                        for(int j=0;j<3;j++)
                          ledState[j] = ledState[j+1];
                        ledState[3] = ledState[0];
                          break;}
               }
            }
         oldbuttonState[i] = buttonState[i];
        }

  //internal logic: compute the 4th LED 
  //computes PARITY
  //ledState[3] = (ledState[0]+ledState[1]+ledState[2])%2;

  //put the output on the LEDs
  for(int i=0;i<4;i++)
    if (ledState[i]==1) digitalWrite(ledOut[i],HIGH);
    else digitalWrite(ledOut[i],LOW);
  
  // send all button states to the serial port
  Serial.print(buttonState[0]);
  Serial.print(buttonState[1]);
  Serial.println(buttonState[2]);

  // 50 ms szunet (to not run through this too fast)
  delay(50);        
}

/*
TRY THE CODE
what do the buttons do?



 */
