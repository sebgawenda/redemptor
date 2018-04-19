package client_package;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Manages all messages sent among clients, printing them to the GUI as well as
 * the console.
 * 
 * @author https://github.com/overgara2
 */
public class VChat_ClientController implements Runnable {
    
    //Globals
    Socket socket;
    Scanner input;
    Scanner send = new Scanner(System.in);
    PrintWriter output;

    /**
     * Simple constructor which takes a client socket and assigns it to a local
     * variable socket.
     * 
     * @param s Socket taken from the client.
     */
    public VChat_ClientController(Socket s) {
        this.socket = s;
    }

    /**
     * Infinite loop which runs as long as the socket is not closed.
     */
    @Override
    public void run() {
        try {
            try {
                input = new Scanner(socket.getInputStream());
                output = new PrintWriter(socket.getOutputStream());
                output.flush();
                
                checkStream();
            }
            finally {
                socket.close();
            }
        } 
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * A method which calls the receive method as long as the client is
     * connected.
     */
    public void checkStream() {
        while (true) {
            receive();
        }                
    }
    
    /**
     * A method which waits for client messages and appends them to the
     * conversation.
     */
    public void receive() {
        if (input.hasNext()) {
            String message = input.nextLine();

            if(message.contains("#?!"))
            {
                String temp = message.substring(3);
                temp = temp.replace("[", "");
                temp = temp.replace("]", "");                    

                String[] currentUsers = temp.split(", ");

                VChat_ClientMain.onlineList.setListData(currentUsers);                    
            }
            else {
                VChat_ClientMain.conversationTA.append(message + "\n");
            }
        }
        }
    
    /**
     * A method which sends the user's message to the chat.
     * @param s 
     */
    public void send(String s) {
        output.println(VChat_ClientMain.userName + ": " + s);
        output.flush();

        VChat_ClientMain.messageTF.setText("");
    }

    /**
     * A method which disconnects the user from the chat room.
     * @throws IOException 
     */
    public void disconnect() throws IOException {
        output.println(VChat_ClientMain.userName + " has disconnected.");
        output.flush();

        socket.close();

        JOptionPane.showMessageDialog(null, "You disconnected!");
        System.exit(0);
    }
}
