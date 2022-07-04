package com.example.rgbjava;

/*
Il Service si occupa di gestire le operazioni di emergenza
In caso di input dal main il service disporra dei metodi per le chiamata di emergenza e altro
Possono essere configurati fino ad un certo punto

Il Service dvrà avviare due thread
Una per le chiamate di emergenza
Ed un altro thread che fara richiesta di accedere alla videocamera

Dopo aver scattato la foto, questa informazione dev'essere gestita a parte da un'altro thread in esecuzione
Il thread in questione inviera la foto scattata ad un e-mail (o altro)
 */

/**
 * Per l'invio dei dati via e-mail 2 soluzioni
 * 1) intent esplicito per inviare i dati mediante app già esistente, ma il successo dell'operazione
 *   dev'essere garantito (slide 04-05)
 * 2) Creare un proprio thread sender con protocollo SMTP
 */

public class Service {

    private User user;

    public Service(){

    }

    /**
     * La start call si occuperà di chiamare i contatti preferiti dell'utente
     * Dando priorità al primo e cosi via
     * Non appena qualcuno risponde sarà adnato a buone fine
     * Altrimenti continuerà a chiamare la lista dei contatti
     */
    public void startCall(){
        // Ciclare un ipotetica lista contatti
        // Effettuerare intent esplicito (sappiamo già come devva avvenire la chiamata)
        // in caso di assenza di linea optare per alternativa già configurata da prima

        /**
         * Piccolo esempio di chiamata
         *         Intent i = new Intent(Intent.ACTION_CALL);
         *         i.setData(Uri.parse("tel:0000000000"));
         *         startActivity(i);
         */
    }

}
