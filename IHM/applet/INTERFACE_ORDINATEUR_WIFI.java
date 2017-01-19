import processing.core.*; 
import processing.xml.*; 

import processing.video.*; 
import processing.net.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class INTERFACE_ORDINATEUR_WIFI extends PApplet {

                                      // importe toute la biblioth\u00e9que vid\u00e9o
Capture camera;                                                 // Cr\u00e9e l'objet Cam\u00e9ra

                                        // importe toute la biblioth\u00e9que r\u00e9seau
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

int position_X_rectangle_droite = position_X_rectangle_gauche + 20 + 2*largeur_bouton;                 // bouton droite \u00e9cart\u00e9 de 20 par rapport au bouton gauche
int position_Y_rectangle_droite = position_Y_rectangle_gauche;                                         // les Y du bouton gauche sont \u00e9gales au Y du bouton droit

int position_X_rectangle_haut = position_X_rectangle_gauche + 10  + largeur_bouton;                    // bouton du haut d\u00e9cal\u00e9 de 10 par rapport au x du bouton gauche
int position_Y_rectangle_haut = position_Y_rectangle_gauche + 10  - hauteur_bouton - hauteur_bouton/5; // bouton haut d\u00e9cal\u00e9 de 1\u00e0 par rapport au y du bouton gauche

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
public void interface_graphique()
{
  size (width, height); // Largeur et Hauteur de fen\u00eatre
  
  arriere_plan=loadImage("honeycomb2.jpg");                                               // Chargement de l'image
  arriere_plan.resize(width, height);                                                     // Adapter l'image \u00e0 la taille de la fenetre 
  background(arriere_plan);                                                               // Mettre l'image en arriere plan
  
  fill(255);                                                                              // Blanc
  textSize(40);                                                                           // Taille de la police = 40
  text("Cam\u00e9ra", 110, 760);
  
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
  smooth();                                                                                       // d\u00e9pixelisation du cercle
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
  
public void setup(){
  
  interface_graphique();
 
  //-----------------------------Capture Video---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
  mon_client = new Client(this, "192.168.43.49", 80);
  
  String[] peripheriques = Capture.list();                                                                // cr\u00e9e une liste de tout les p\u00e9riph\u00e9riques permettant une capture video 
  println("P\u00e9riph\u00e9riques de capture vid\u00e9o: ");
  println("");
  println(peripheriques);
  camera = new Capture(this, largeur_camera, hauteur_camera, peripheriques [0]);                          // hauteur et largeur d\u00e9finit par d\u00e9faut et devices [n\u00b0] correspond au port o\u00f9 la camera est branch\u00e9e  
  camera.settings();                                                                                // Fenetre permettant de configurer les param\u00e8tre video de la cam\u00e9ra 
}

public void  draw()                                                                                        // Fonction de raffraichissement de la video 
{
  if (camera.available() == true)                                                                   // Boucle "si la camera est activ\u00e9e" alors ...
  {
    camera.read();                                                                                  // lire les images recu par la camera
    image(camera, width - largeur_camera - 100 , 20);                                               // cr\u00e9e une image issu de l'objet camera (camera branch\u00e9e au syst\u00e8me)
  }
} 


public void keyPressed (){                                                                                 // Si le doigt touche la zone d\u00e9finie (si je clique sur la zone d\u00e9finie)
 
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
   
     u=++u;                                                                                          // incr\u00e9mente u 
     octet_envoyee=u+48;
     mon_client.write(octet_envoyee);                                                                // valeur initiale + 48 pour format ASCII - envoie valeur 0 \u00e0 9  
  }
    if (keyCode == DOWN & u>-3)                                                                             // Rectangle bas
  {
    fill(0,0,255);                                                                                   // Bleu
    stroke(0);                                                                                       // Bordure noire
    rect(position_X_rectangle_bas, position_Y_rectangle_bas, largeur_bouton , hauteur_bouton);       //rectangle bouton bas
    
    u=--u; // d\u00e9cr\u00e9mente u 
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
     y=++y;                                                                                          // Incremente Y de 1 \u00e0 chaque clique 
     fill(0,0,255);                                                                                  // BLEU
     stroke(0);                                                                                      // Bordure noire
     triangle(position_X_triangle_haut, position_Y_triangle_haut, width - 300, position_Y_triangle_haut, width - 350, position_Y_triangle_haut - hauteur_bouton);// (x1,y1,x2,y2,x3,y3) triangle du haut
  }
    
    if(key == 's' & y > 54)                                                                          // Triangle bas
  {
     y=--y;                                                                                          // Desincremente Y de 1 \u00e0 chaque clique
     fill(0,0,255);                                                                                  // BLEU
     stroke(0);                                                                                      // Bordure noire
     triangle(position_X_triangle_bas, position_Y_triangle_bas, width - 300, position_Y_triangle_bas, width - 350, position_Y_triangle_bas + hauteur_bouton);// (x1,y1,x2,y2,x3,y3) triangle du bas
  }
}

public void keyReleased()
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
    rect(30,605,35,10);                                                                                 // Corps de la fl\u00e9che
    triangle(30,600,30,620,15,610);                                                                     // Triangle 
    
    stroke(44,54,240);
    fill(255);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20-(hauteur_jauge/2) , largeur_jauge, - hauteur_jauge/2);// Le dessus reste blanc
    
    y = 55;                                                                                             // Permettre de r\u00e9duire ou d'augmenter la vitesse apr\u00e8s rotation
  }
  
  if (key == 'd')                                                                                       // Triangle droite
  {    
    fill(255);                                                                                          // Blanc
    stroke(0);                                                                                          // Bordure noire
    triangle(position_X_triangle_droite, position_Y_triangle_droite, width - 300, position_Y_triangle_droite + hauteur_bouton, width - 200, position_Y_triangle_droite + (hauteur_bouton)/2 );// (x1,y1,x2,y2,x3,y3) triangle du droite

    fill(255,0,0);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20 , largeur_jauge, - hauteur_jauge/2); // Rotation a gauche Vitesse 2
   
    fill(0);                                                                                            // Noir
    rect(15,605,35,10);                                                                                 // Corps de la fl\u00e9che
    triangle(50,600,50,620,65,610);                                                                     // Triangle 
    
    stroke(44,54,240); 
    fill(255);
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 20-(hauteur_jauge/2) , largeur_jauge, - hauteur_jauge/2);// Le dessus reste blanc
    
    y = 55;                                                                                             // Permettre de r\u00e9duire ou d'augmenter la vitesse apr\u00e8s rotation
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

public void mousePressed(){ 
  
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
    y = 54;                                                             // Remettre la vitesse des moteurs \u00e0 0 
  }
}

public void mouseReleased ()
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
    rect(position_X_rectangle_gauche - largeur_bouton - 80, height - 780 , largeur_jauge, hauteur_jauge);//Vitesse \u00e0 0 toute la jauge est blanche
  }
}
  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#F0F0F0", "INTERFACE_ORDINATEUR_WIFI" });
  }
}
