//---------------------------LIBRAIRIE IMPORTEE-----------------------------------------//
  #include <Servo.h>//Insertion de la librairie Servomoteur
  
  #include <WiFi.h>
  #include <WiFiClient.h>
  #include <WiFiServer.h>

//---------------------------POSITION SERVOMOTEUR 180°-----------------------------------//

  int position_du_180=90;                                   // Variable positionnant le servomoteur à 90°

//---------------------------POSITION SERVOMOTEUR 360°-----------------------------------//
  int sens_trigo=1700;                                      // Largeur d'impulsion correspondant à un déplacement dans le sens trigonométrique
  int sens_horaire=1300;                                    // Largeur d'impulsion correspondant à un déplacement dans le sens horaire

//---------------------------BROCHES SERVOMOTEURS----------------------------------------//
  const int BROCHE_SERVO180=6;                              // declaration constante de la broche du servomoteur de 180°
  const int BROCHE_SERVO360=8;                              // declaration constante de la broche du servomoteur de 360°

//---------------------------VARIABLE RECEPTION DE DONNEES -------------------------------//
  int octet_recu=0;                                         // variable stockant les valeurs recus en octet

//---------------------------OBJETS------------------------------------------------------//
  Servo servo180;                                           // crée l'objet Servomoteur 180° 
  Servo servo360;                                           // crée l'objet Servomoteur 360°

//---------------------------VARIATEURS-------------------------------------------------//
  const int BROCHE_VARIATEURGAUCHE=12;
  const int BROCHE_VARIATEURDROITE=11;
  
//---------------------------FONCTION WIFI-----------------------------------------------//
char ssid[] = "arduino 2009";                               // nom du réseau
WiFiServer server(80);                                      // crée l'objet serveur sur le port 80
WiFiClient client;                                          // crée l'objet client c'est à dire l'ordinateur ou la tablette

//----------------------------FONCTIONS DU PROGRAMME-------------------------------------//
void variateur_gauche()
{
  if(octet_recu == 54 || octet_recu == 61){                 // 61 : Arret d'urgence
  analogWrite(BROCHE_VARIATEURGAUCHE, 0);
  }
  if(octet_recu == 55){                                     // VITESSE 1: 300 tr/min
  analogWrite(BROCHE_VARIATEURGAUCHE, 59);
  }
  if(octet_recu == 56){                                     // VITESSE 2: 800 tr/min
  analogWrite(BROCHE_VARIATEURGAUCHE, 90);
  }
  if(octet_recu == 57){                                     // VITESSE 3: 1200 tr/min
  analogWrite(BROCHE_VARIATEURGAUCHE, 120);
  }
  if(octet_recu == 58){                                     // VITESSE 4: 1750 tr/min
  analogWrite(BROCHE_VARIATEURGAUCHE, 255);
  } 
  if(octet_recu == 60){                                     // Tourner à Droite
  analogWrite(BROCHE_VARIATEURGAUCHE, 90);
  }
}
//---------------------------------------------------------------------------------------//
void variateur_droite()
{
  if(octet_recu == 54 || octet_recu == 60 || octet_recu == 61){      // 61 : Arret d'urgence
  analogWrite(BROCHE_VARIATEURDROITE, 0);
  }
  if(octet_recu == 55){                                     // VITESSE 1: 300 tr/min
  analogWrite(BROCHE_VARIATEURDROITE, 59);
  }
  if(octet_recu == 56){                                     // VITESSE 2: 800 tr/min
  analogWrite(BROCHE_VARIATEURDROITE, 90);
  }
  if(octet_recu == 57){                                     // VITESSE 3: 1200 tr/min
  analogWrite(BROCHE_VARIATEURDROITE, 120);
  }
  if(octet_recu == 58){                                     // VITESSE 4: 1750 tr/min
  analogWrite(BROCHE_VARIATEURDROITE, 255);
  }
  if(octet_recu == 59){                                     // Tourner à gauche
  analogWrite(BROCHE_VARIATEURDROITE, 90);
  }  
}
//---------------------------------------------------------------------------------------// 
void servomoteur_de_180()
{
  octet_recu=octet_recu-48;                                 // isole les valeurs numeriques
  position_du_180=map(octet_recu,0,3,90,180);               // calcule la nouvelle position 
  servo180.write(position_du_180);                          // positionne le servomoteur 
} 
//---------------------------------------------------------------------------------------//  
void servomoteur_de_360_sens_trigo()
{
  servo360.attach(BROCHE_SERVO360);                          // attache le servomoteur de 360° à la broche 8
  servo360.write(sens_trigo);                                // Le servomoteur se déplace dans le sens trigonométrique
  delay(125);                                                // Avec un angle correspondant à 30° 
  servo360.detach();                                         // détache le servomoteur de la broche pour empecher sa rotation continue une fois l'angle atteint
  octet_recu= octet_recu-52;                                 // remet à 0 la variable de lecture
} 
//---------------------------------------------------------------------------------------// 
void servomoteur_de_360_sens_horaire()
{
  servo360.attach(BROCHE_SERVO360);                          // attache le servomoteur de 360° à la broche 8
  servo360.write(sens_horaire);                              // Le servomoteur se déplace dans le sens horaire
  delay(125);                                                // Avec un angle correspondant à 30° 
  servo360.detach();                                         // détache le servomoteur de la broche pour empecher sa rotation continue une fois l'angle atteint
  octet_recu= octet_recu-52;                                 // remet à 0 la variable de lecture
}

//---------------------------INITIALISATION DU PROGRAMME---------------------------------//
void setup() //Initialisation 
{ 
  Serial.begin(9600);
   
  WiFi.begin(ssid);                                     // commence la connexion au réseau wifi
  Serial.println(WiFi.localIP());    
  server.begin();
  Serial.print(WiFi.localIP()); 
  
  servo180.attach(BROCHE_SERVO180);                           // Attache le servomoteur à la broche 6 mais désactive la 9 et la 10
  pinMode(BROCHE_SERVO180, OUTPUT);                           // Broche 6 en sortie(servomoteur de 180°)
  servo180.write(position_du_180);                            // affecte au servomoteur180 --> 90° initialement  
} 

//---------------------------BOUCLE DU PROGRAMME-------------------------------------------// 
void loop()
{ 
  client = server.available();                                // analyser les clients entrants  
  octet_recu = client.read();                                 // lire le 1er octet recu et le garde en memoire
  
  variateur_gauche(), variateur_droite();
  
  if(octet_recu >=45 & octet_recu < 52)                       // ne pas déborder au dela des 180° prévu
  { 
  servomoteur_de_180();  
  }
  
  if (octet_recu == 52)
  {
  servomoteur_de_360_sens_trigo();
  }
  
  if (octet_recu == 53)
  {
  servomoteur_de_360_sens_horaire();
  }
  
}


