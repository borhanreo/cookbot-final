package w3engineers.com.cookerbot.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


import w3engineers.com.cookerbot.R;
import w3engineers.com.cookerbot.bluetooth.DeviceListActivity;

public class IoTActivity extends AppCompatActivity {
    private String TAG = "borhan";

    private static final int MESH_PORT = 1166;

    private Button configure, send_temp;
    private static String address;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private ConnectedThread mConnectedThread;
    final int handlerState = 0;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Button btnOn, btnOff,chicken,beef,rice,noodles;
    TextView txtArduino, txtString, txtStringLength, sensorView0,readSerialData;
    Handler bluetoothIn;
    private StringBuilder recDataString = new StringBuilder();
    String dataInPrint;
    int dataLength;
    private String globalTemp = "";
    long millisecondMe = 0;
    int refreshTime = 5000;
    private long previousRuntime = 0L;
    private final long TIME_DELAY = 5000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io_t);
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                Log.d(TAG, "  " + readMessage);

            }
        };


        btnOn = (Button) findViewById(R.id.buttonOn);
        btnOff = (Button) findViewById(R.id.buttonOff);

        chicken = (Button) findViewById(R.id.chicken);
        beef = (Button) findViewById(R.id.beef);
        rice = (Button) findViewById(R.id.rice);
        noodles = (Button) findViewById(R.id.noodles);
        readSerialData= (TextView) findViewById(R.id.readSerialData);
        chicken.setVisibility(View.INVISIBLE);
        beef.setVisibility(View.INVISIBLE);
        rice.setVisibility(View.INVISIBLE);
        noodles.setVisibility(View.INVISIBLE);

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    //Log.d(TAG," rrr "+readMessage.toString());
                    recDataString.append(readMessage);
                    int endOfLineIndex = recDataString.indexOf("~");
                    if (endOfLineIndex > 0) {
                        dataInPrint = recDataString.substring(0, endOfLineIndex);
                        dataLength = dataInPrint.length();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (recDataString.charAt(0) == '#') {
                                    /*String sensor0 = recDataString.substring(1, 5);
                                    String sensor1 = recDataString.substring(6, 10);
                                    String sensor2 = recDataString.substring(11, 15);
                                    String sensor3 = recDataString.substring(16, 20);
                                    //Log.d(TAG," temp "+sensor0);
                                    sensorView0.setText(" Current Temperature = " + sensor0 + " C");
                                    globalTemp = sensor0 + "";*/
                                    readSerialData.setText("");
                                    readSerialData.setText(recDataString.toString());
                                }

                                recDataString.delete(0, recDataString.length());
                                Log.d(TAG,"borhan"+recDataString.toString());
                            }
                        });
                        //Log.d(TAG, " size " + userList.size());
                        //sendData();
                        dataLength = 0;
                        dataInPrint = " ";
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();


        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
        btnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("A");    // Send "0" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn off Device", Toast.LENGTH_SHORT).show();

            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("B");    // Send "1" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn on Devices", Toast.LENGTH_SHORT).show();
                chicken.setVisibility(View.VISIBLE);
                beef.setVisibility(View.VISIBLE);
                rice.setVisibility(View.VISIBLE);
                noodles.setVisibility(View.VISIBLE);
            }
        });
        chicken.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("C");    // Send "0" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn off Device", Toast.LENGTH_SHORT).show();
            }
        });

        beef.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("E");    // Send "1" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn on Devices", Toast.LENGTH_SHORT).show();
            }
        });
        rice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("D");    // Send "0" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn off Device", Toast.LENGTH_SHORT).show();
            }
        });

        noodles.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("F");    // Send "1" via Bluetooth
                Toast.makeText(getBaseContext(), "Turn on Devices", Toast.LENGTH_SHORT).show();
            }
        });
    }







        public void uu()
        {
            /*try {
                if(message.equals("1.11"))
                {
                    mConnectedThread.write("1");
                }else if(message.equals("0.00"))
                {
                    mConnectedThread.write("0");
                }
            }catch (Exception e)
            {

            }*/
        }

    @Override
    protected void onResume() {
        super.onResume();
        getDeviceAddress();

    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    private void getDeviceAddress() {
        Intent intent = getIntent();
        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);
        Log.d(TAG, " address " + device.getAddress());
        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
        mConnectedThread.write("x");
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
}
