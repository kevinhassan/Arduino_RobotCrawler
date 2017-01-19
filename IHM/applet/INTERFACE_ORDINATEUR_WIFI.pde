import processing.video.*;                                      // importe toute la bibliothéque vidéo
Capture camera;                                                 // Crée l'objet Caméra

import processing.net.*;                                        // importe toute la bibliothéque réseau
Client mon_client;
int octet_envoyee=0;

int hauteur_camera = 380;
int largeur_camera = 500;
//----------------------------FENETRE----------------------------------------------------//
int width = 1280;
int height = 800;

//----------------------------JAUGE DE VITESSE------------------------------------------//
int hauteur_jauge = 760;
int largeur_jauge = 60;
//---------------------------BOUTON CAMERA----------------------------------------------//
int hauteur_bouton= 100;
int largeur_bouton= 100;

int position_X_rectangle_gauche = width - 1090;
int position_Y_rectangle_gauche = height - 250;

int position_X_rectangle_droite = position_X_rectangle_gauche + 20 + 2*largeur_bouton;                 // bouton droite écarté de 20 par rapport au bouton gauche
int position_Y_rectangle_droite = position_Y_rectangle_gauche;                                         // les Y du bouton gauche sont égales au Y du bouton droit

int position_X_rectangle_haut = position_X_rectangle_gauche + 10  + largeur_bouton;                    // bouton du haut décalé de 10 par rapport au x du bouton gauche
int position_Y_rectangle_haut = position_Y_rectangle_gauche + 10  - hauteur_bouton - hauteur_bouton/5; // bouton haut décalé de 1à par rapport au y du bouton gauche

int position_X_rectangle_bas = position_X_rectangle_gauche + 10 + largeur_bouton;
int position_Y_rectangle_bas = position_Y_rectangle_gauche - 10 + hauteur_bouton + hauteur_bouton/5 ;

//----------------------------BOUTONS MOTORISATION--------------------------------------//

int position_X_triangle_gauche = width - 400;
int position_Y_triangle_gauche = position_Y_rectangle_gauche;

int position_X_triangle_droite = width - 300;
int position_Y_triangle_droite = position_Y_triangle_gauche;

int position_X_triangle_haut = position_X_triangle_gauche;
int position_Y_triangle_haut = position_Y_rectangle_haut + hauteur_bouton;

int position_X_triangle_bas = width - 400;
int position_Y_triangle_bas = position_Y_rectangle_bas;

//----------------------------BOUTONS ARRET D'URGENCE----------------------------------//

int position_X_bouton_arret = (width - 400 + width - 300) /2 ;
int position_Y_bouton_arret = (position_Y_triangle_haut + position_Y_triangle_bas) /2 ;

PImage arriere_plan;                                                                      // objet image d'arriere plan de la fenetre

//---------------------------VARIABLES D'INCREMENTATIONS-------------------------------//
int t=61;
int u=0; 
int x=0;
int y=54;
int z=0;
//---------------------------FONCTION INITIALISATION-----------------------------------//
void interface_graphique()
{
  size (width, height); // Largeur et Hauteur de fenêtre
  
  arriere_plan=loadImage("honeycomb2.jpg");                                               // Chargement de l'image
  arriere_plan.resize(width, height);                                                     // Adapter l'image à la taille de la fenetre 
  background(arriere_plan);                                                               // Mettre l'image en arriere plan
  
  fill(255);                                                                              // Blanc
  textSize(40);                                                                           // Taille de la police = 40
  text("Caméra", 110, 760);
  
  fill(255);                                                                              // Blanc
  textSize(40);                                                                           // Taille de la police
  text("Moteur", 710, 760);
  
  //----------------------------BOUTONS CAMERA-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
 
  stroke(44,54,240);                                                                      // Bordure Bleu 
  fill(0,0,0,0);                                                                          // 100 % transparent 
  rect(position_X_rectangle_gauche - largeur_bouton, position_Y_rectangle_haut - 20, position_X_rectangle_droite + largeur_bouton, 3*hauteur_bouton + 60);     // cadre Bouton rectangulaire
  
  fill(255);                                                                              //Blanc
  stroke(0);                                                                              //Bordure noire
  rect(position_X_rectangle_gauche, position_Y_rectangle_gauche, largeur_bouton , hauteur_bouton);                                                            // rectangle bouton gauche 
  
  fill(255);                                                                              //Blanc
  stroke(0);                                                                              //Bordure noire
  rect(position_X_rectangle_droite, position_Y_rectangle_droite, largeur_bouton , hauteur_bouton);                                                            // rectangle bouton droite 
  
  fill(255);                                                                                    //Blanc
  stroke(0);                                                                                    //Bordure noire
  rect(position_X_rectangle_haut, position_Y_rectangle_haut, largeur_bouton , hauteur_bouton);  // rectangle bouton haut 
  
  fill(255);// Blanc
  stroke(0);// Bordure noire
  rect(position_X_rectangle_bas, position_Y_rectangle_bas, largeur_bouton , hauteur_bouton);    // rectangle bouton bas 
  
  
  //-------------------------BOUTONS MOTEUR---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//   
  
  fill(255);                                                                                    // Blanc
  stroke(0);                                                                                    // Bordure noire
  triangle(position_X_triangle_gauche, position_Y_triangle_gauche, width - 400, position_Y_triangle_gauche + hauteur_bouton, width - 500, position_Y_triangle_gauche + (hauteur_bouton)/2 );  // (x1,y1,x2,y2,x3,y3) triangle du gauche
  
  fill(255);                                                                                    // Blanc
  stroke(0);                                                                                    // Bordure noire
  triangle(position_X_triangle_droite, position_Y_triangle_droite, width - 300, position_Y_triangle_droite + hauteur_bouton, width - 200, position_Y_triangle_droite + (hauteur_bouton)/2 );  // (x1,y1,x2,y2,x3,y3) triangle du droite
  
  fill(255);                                                                                     // Blanc
  stroke(0);                                                                                     // Bordure noire
  triangle(position_X_triangle_haut, position_Y_triangle_haut, width - 300, position_Y_triangle_haut, width - 350, position_Y_triangle_haut - hauteur_bouton);// (x1,y1,x2,y2,x3,y3) triangle du haut
  
  fill(255);                                                                                     // Blanc
  stroke(0);                                                                                     // Bordure noire
  triangle(position_X_triangle_bas, position_Y_triangle_bas, width - 300, position_Y_triangle_bas, width - 350, position_Y_triangle_bas + hauteur_bouton);    // (x1,y1,x2,y2,x3,y3) triangle du bas

  //---------------------------BOUTONS ARRET D'URGENCE------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

  fill(255,0,0);                                                                                  // Rouge
  smooth();                                                                                       // dépixelisation du cercle
  ellipse( position_X_bouton_arret, position_Y_bouton_arret,80,80);                               // Cercle 
  
  fill(0);                                                                                        // Noire
  textSize(80);                                                                                   // taille police = 80
  text("!",position_X_bouton_arret - 15 ,position_Y_bouton_arret + 30);

  stroke(44,54,240);                                                                               // Bordure Bleu
  fill(0,0,0,0);                                                                                   //100 % transparent 
  rect(position_X_triangle_gauche - 2*largeur_bouton, position_Y_triangle_haut - hauteur_bouton - 20, 5*largeur_bouton, 3*hauteur_bouton + 60); // cadre Bouton rectangulaire
  
  //------------------------JAUGE DE VITESSE ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
  fill(255);
  rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 780 , largeur_jauge, hauteur_jauge);
}
  
void setup(){
  
  interface_graphique();
 
  //-----------------------------Capture Video---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
  mon_client = new Client(this, "192.168.43.49", 80);
  
  String[] peripheriques = Capture.list();                                                                // crée une liste de tout les périphériques permettant une capture video 
  println("Périphériques de capture vidéo: ");
  println("");
  println(peripheriques);
  camera = new Capture(this, largeur_camera, hauteur_camera, peripheriques [0]);                          // hauteur et largeur définit par défaut et devices [n°] correspond au port où la camera est branchée  
  camera.settings();                                                                                // Fenetre permettant de configurer les paramètre video de la caméra 
}

void  draw()                                                                                        // Fonction de raffraichissement de la video 
{
  if (camera.available() == true)                                                                   // Boucle "si la camera est activée" alors ...
  {
    camera.read();                                                                                  // lire les images recu par la camera
    image(camera, width - largeur_camera - 100 , 20);                                               // crée une image issu de l'objet camera (camera branchée au système)
  }
} 


void keyPressed (){                                                                                 // Si le doigt touche la zone définie (si je clique sur la zone définie)
 
 //---------------------BOUTON CAMERA -------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
if(key == CODED){ 
  
  if (keyCode == LEFT)                                                                               // Rectangle gauche
  {
    fill(0,0,255);                                                                                   // Bleu
    stroke(0);                                                                                       // Bordure noire
    rect(position_X_rectangle_gauche, position_Y_rectangle_gauche, largeur_bouton , hauteur_bouton); // rectangle bouton gauche 
    
    x=53;
    octet_envoyee=x;
    mon_client.write(octet_envoyee);
  }
  
  if (keyCode == RIGHT)                                                                              // Rectangle droite
  {
    fill(0,0,255);// Bleu
    stroke(0);// Bordure noire
    rect(position_X_rectangle_droite, position_Y_rectangle_droite, largeur_bouton , hauteur_bouton);//rectangle bouton droite 
   
    x=52;
    octet_envoyee=x;
    mon_client.write(octet_envoyee);
  }
    if (keyCode == UP & u<3)                                                                         // Rectangle haut
  {
    fill(0,0,255);                                                                                   // Bleu
    stroke(0);                                                                                       // Bordure noire
    rect(position_X_rectangle_haut, position_Y_rectangle_haut, largeur_bouton , hauteur_bouton);     // rectangle bouton haut 
   
     u=++u;                                                                                          // incrémente u 
     octet_envoyee=u+48;
     mon_client.write(octet_envoyee);                                                                // valeur initiale + 48 pour format ASCII - envoie valeur 0 à 9  
  }
    if (keyCode == DOWN & u>-3)                                                                             // Rectangle bas
  {
    fill(0,0,255);                                                                                   // Bleu
    stroke(0);                                                                                       // Bordure noire
    rect(position_X_rectangle_bas, position_Y_rectangle_bas, largeur_bouton , hauteur_bouton);       //rectangle bouton bas
    
    u=--u; // décrémente u 
    octet_envoyee=u+48;
    mon_client.write(octet_envoyee);   //  45 =< octet envoye <=51  
  }
}
 //------------BOUTON MOTEUR-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
   if (key == 'q')                                                                                   // Triangle gauche
  {
    fill(0,0,255);                                                                                   // BLEU
    stroke(0);
    triangle(position_X_triangle_gauche, position_Y_triangle_gauche, width - 400, position_Y_triangle_gauche + hauteur_bouton, width - 500, position_Y_triangle_gauche + (hauteur_bouton)/2 );// (x1,y1,x2,y2,x3,y3) triangle du gauche
  } 

    if (key == 'd')                                                                                  // Triangle droite
  {
    fill(0,0,255);                                                                                   // BLEU
    stroke(0);
    triangle(position_X_triangle_droite, position_Y_triangle_droite, width - 300, position_Y_triangle_droite + hauteur_bouton, width - 200, position_Y_triangle_droite + (hauteur_bouton)/2 );// (x1,y1,x2,y2,x3,y3) triangle du droite
  }  
    if(key == 'z' & y < 58 )                                                                         // Triangle haut
  {
     y=++y;                                                                                          // Incremente Y de 1 à chaque clique 
     fill(0,0,255);                                                                                  // BLEU
     stroke(0);                                                                                      // Bordure noire
     triangle(position_X_triangle_haut, position_Y_triangle_haut, width - 300, position_Y_triangle_haut, width - 350, position_Y_triangle_haut - hauteur_bouton);// (x1,y1,x2,y2,x3,y3) triangle du haut
  }
    
    if(key == 's' & y > 54)                                                                          // Triangle bas
  {
     y=--y;                                                                                          // Desincremente Y de 1 à chaque clique
     fill(0,0,255);                                                                                  // BLEU
     stroke(0);                                                                                      // Bordure noire
     triangle(position_X_triangle_bas, position_Y_triangle_bas, width - 300, position_Y_triangle_bas, width - 350, position_Y_triangle_bas + hauteur_bouton);// (x1,y1,x2,y2,x3,y3) triangle du bas
  }
}

void keyReleased()
{
if (key == CODED){ 

  if (keyCode == LEFT)                                                                               // Rectangle gauche
  {       
    fill(255);                                                                                       // Blanc
    stroke(0);                                                                                       // Bordure noire
    rect(position_X_rectangle_gauche, position_Y_rectangle_gauche, largeur_bouton , hauteur_bouton); //rectangle bouton gauche 
  }
  
  if (keyCode == RIGHT)                                                                              // Rectangle droite
  {   
    fill(255);                                                                                       // Blanc
    stroke(0);                                                                                       // Bordure noire
    rect(position_X_rectangle_droite, position_Y_rectangle_droite, largeur_bouton , hauteur_bouton); // Rectangle bouton droite 
  }
  
  if (keyCode == UP)                                                                                  // Rectangle haut
  {    
    fill(255);                                                                                        // Blanc
    stroke(0);                                                                                        // Bordure noire
    rect(position_X_rectangle_haut, position_Y_rectangle_haut, largeur_bouton , hauteur_bouton);      // Rectangle bouton haut 
  }
  
  if (keyCode == DOWN)                                                                                // Rectangle bas
  {    
    fill(255);                                                                                        // Blanc
    stroke(0);                                                                                        // Bordure noire
    rect(position_X_rectangle_bas, position_Y_rectangle_bas, largeur_bouton , hauteur_bouton);        // Rectangle bouton bas 
  }
}
  
  //-------------------BOUTON MOTEUR------------------------------------------------------------------------------------------//
    
  if (key == 'q')                                                                                      // Triangle gauche
  {
    fill(255);                                                                                         // Blanc
    stroke(0);                                                                                         // Bordure noire
    triangle(position_X_triangle_gauche, position_Y_triangle_gauche, width - 400, position_Y_triangle_gauche + hauteur_bouton, width - 500, position_Y_triangle_gauche + (hauteur_bouton)/2 );// (x1,y1,x2,y2,x3,y3) triangle du gauche
    
    fill(255,0,0);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20 , largeur_jauge, - hauteur_jauge/2); // Rotation a gauche Vitesse 2
    
    fill(0);                                                                                            // Noir
    rect(30,605,35,10);                                                                                 // Corps de la fléche
    triangle(30,600,30,620,15,610);                                                                     // Triangle 
    
    stroke(44,54,240);
    fill(255);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20-(hauteur_jauge/2) , largeur_jauge, - hauteur_jauge/2);// Le dessus reste blanc
    
    y = 55;                                                                                             // Permettre de réduire ou d'augmenter la vitesse après rotation
  }
  
  if (key == 'd')                                                                                       // Triangle droite
  {    
    fill(255);                                                                                          // Blanc
    stroke(0);                                                                                          // Bordure noire
    triangle(position_X_triangle_droite, position_Y_triangle_droite, width - 300, position_Y_triangle_droite + hauteur_bouton, width - 200, position_Y_triangle_droite + (hauteur_bouton)/2 );// (x1,y1,x2,y2,x3,y3) triangle du droite

    fill(255,0,0);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20 , largeur_jauge, - hauteur_jauge/2); // Rotation a gauche Vitesse 2
   
    fill(0);                                                                                            // Noir
    rect(15,605,35,10);                                                                                 // Corps de la fléche
    triangle(50,600,50,620,65,610);                                                                     // Triangle 
    
    stroke(44,54,240); 
    fill(255);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20-(hauteur_jauge/2) , largeur_jauge, - hauteur_jauge/2);// Le dessus reste blanc
    
    y = 55;                                                                                             // Permettre de réduire ou d'augmenter la vitesse après rotation
  }
  
  if (key == 'z')                                                                                       // Triangle haut
  {    
    fill(255);                                                                                          // Blanc
    stroke(0);                                                                                          // Bordure noire
    triangle(position_X_triangle_haut, position_Y_triangle_haut, width - 300, position_Y_triangle_haut, width - 350, position_Y_triangle_haut - hauteur_bouton);// (x1,y1,x2,y2,x3,y3) triangle du haut
    
    if (y == 55)                                                                                        // Vitesse 1
    {
    fill(255,0,0);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20 , largeur_jauge, - hauteur_jauge/4);
    }
    
    if (y == 56)                                                                                        // Vitesse 2
    {
    fill(255,0,0);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20 , largeur_jauge, - hauteur_jauge/2);
    }
    
    if (y == 57)                                                                                        // Vitesse 3
    {
    fill(255,0,0);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20 , largeur_jauge, - (3*hauteur_jauge)/4);
    } 
   
    if (y == 58)                                                                                        // Vitesse 4
    {
    fill(255,0,0);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20 , largeur_jauge, - hauteur_jauge);
    }
}
  
  if(key == 's')                                                                                        // Triangle bas
  {
    fill(255);                                                                                          // Blanc
    stroke(0);                                                                                          // Bordure noire
    triangle(position_X_triangle_bas, position_Y_triangle_bas, width - 300, position_Y_triangle_bas, width - 350, position_Y_triangle_bas + hauteur_bouton);// (x1,y1,x2,y2,x3,y3) triangle du bas
    
    if (y == 57)                                                                                        // Vitesse 3
    {

    fill(255);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - hauteur_jauge - 20 , largeur_jauge, + hauteur_jauge/4);
    }
    
    if (y == 56)                                                                                         // Vitesse 2
    {
    stroke(44,54,240);
    fill(255);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - hauteur_jauge - 20 , largeur_jauge, + hauteur_jauge/2);
    }
    
    if (y == 55)                                                                                          // Vitesse 1
    {
    stroke(44,54,240);
    fill(255);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - hauteur_jauge - 20 , largeur_jauge, + (3*hauteur_jauge)/4);
    } 
   
    if (y == 54)                                                                                          // Vitesse 0
    {
    stroke(44,54,240);
    fill(255);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - hauteur_jauge - 20 , largeur_jauge, + hauteur_jauge);
    }
  }
}

void mousePressed(){ 
  
  //--------------------------------BOUTON ARRET D'URGENCE---------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    if( pmouseX >= (position_X_bouton_arret - 40) & pmouseY >= (position_Y_bouton_arret - 40) & pmouseX <= (position_X_bouton_arret + 40) & pmouseY <= (position_Y_bouton_arret + 40) )// Bouton Arret D'urgence
  {
    stroke(0);                                                          // Bordure noire
    fill(255);                                                          // Blanc
    smooth();                                                           // depixeliser le cercle 
    ellipse( position_X_bouton_arret, position_Y_bouton_arret,80,80);   // cercle 
    
    fill(0);                                                            // Noire
    textSize(80);                                                       // Taille de la police = 80
    text("!",position_X_bouton_arret - 15 ,position_Y_bouton_arret + 30);
    y = 54;                                                             // Remettre la vitesse des moteurs à 0 
  }
}

void mouseReleased ()
{
  //---------------------BOUTON CAMERA---------------------------------------------------------------------------------------//
    delay(200);                                                          // Temporisation de 200 ms 
  
  //------------------BOUTON ARRET D'URGENCE------------------------------------------------------------------------------------//
  
  if( pmouseX >= (position_X_bouton_arret - 40) & pmouseY >= (position_Y_bouton_arret - 40) & pmouseX <= (position_X_bouton_arret + 40) & pmouseY <= (position_Y_bouton_arret + 40) )// Bouton Arret D'urgence  
  {  
    fill(255,0,0);                                                       // Rouge
    smooth();                                                            // depixeliser le cercle
    ellipse( position_X_bouton_arret, position_Y_bouton_arret,80,80);    // cercle
    
    fill(0);                                                             // Noire
    textSize(80);                                                        // Taille de la police = 80
    text("!",position_X_bouton_arret - 15 ,position_Y_bouton_arret + 30);
    
  //------------------------JAUGE DE VITESSE ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    stroke(44,54,240);                                                   // Bordure Bleu
    fill(255);                                                           // Blanc
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 780 , largeur_jauge, hauteur_jauge);//Vitesse à 0 toute la jauge est blanche
  }
}
