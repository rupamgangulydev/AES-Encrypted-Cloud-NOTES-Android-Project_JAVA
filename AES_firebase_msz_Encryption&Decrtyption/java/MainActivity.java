package com.example.encr;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    // References:
    EditText editText;
    Button button;
    ListView listView;
    DatabaseReference databaseReference;
    ArrayList<String> list;
    ArrayAdapter arrayAdapter;
    byte encryptionKey[] = {9,115,51,86,105,4,-31,-23,-68,88,17,20,3,-105,119,-53};
    SecretKeySpec secretKeySpec= new SecretKeySpec(encryptionKey,"AES");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("AES encryption APP");

        //Initialization:
        editText= findViewById(R.id.type_here_button);
        button= findViewById(R.id.send_button);
        listView= findViewById(R.id.list_view);
        databaseReference= FirebaseDatabase.getInstance().getReference("Message");
        list=new ArrayList<>();
        arrayAdapter= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);

        listView.setAdapter(arrayAdapter);

        // Button Click Event handling
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date= new Date();
                    try {
                        databaseReference.child(Long.toString(date.getTime())).setValue(encryptionMethodAES(editText.getText().toString()));
                    }
                    catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                editText.setText("");
            }
        });
        // Send Button End
        // get data from database and show in listview...
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snaps: snapshot.getChildren()){
                    String item= snaps.getValue().toString();
                    // decryption method calling from here before add it onto listview...
//                    item=item.substring(1,item.length()-1);// remove padding

                    String decMsz= null;
                    try {
                        decMsz = decryptionMethodAES(item);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }
                    list.add(decMsz);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String encryptionMethodAES(String entrytext) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        // convert string into bytes
        byte[] entrytextBytes=entrytext.getBytes();
        //Output of encrpted data stores in encryptedBytes variable array,
        byte[] encryptedBytes= new byte[entrytextBytes.length];


        //In order to create a Cipher object, the application calls the Cipher's getInstance method,
        // and passes the name of the requested transformation to it.
       // A transformation always includes the name of a cryptographic algorithm (e.g., DES),
        Cipher cipher = Cipher.getInstance("AES");
        //SecretKeySpec used to construct a SecretKey from a byte array,
//        SecretKeySpec secretKeySpec= new SecretKeySpec(encryptionKey,"AES");

        //Initializes this cipher with a key.
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        //Finishes a multiple-part encryption or decryption operation, depending on how this cipher was initialized.
        encryptedBytes= cipher.doFinal(entrytextBytes);// here it mainly convert it

        String outputOfEncryption = new String(encryptedBytes, "ISO-8859-1");
        return outputOfEncryption;
    }
    private String decryptionMethodAES(String dbText) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] EncryptedByte= dbText.getBytes("ISO-8859-1");
        Cipher dicipher=Cipher.getInstance("AES");
        dicipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] dec=dicipher.doFinal(EncryptedByte);
        String decString= new String(dec);
        return decString;
    }
}