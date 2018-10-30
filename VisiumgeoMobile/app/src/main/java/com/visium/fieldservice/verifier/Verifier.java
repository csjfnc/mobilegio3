package com.visium.fieldservice.verifier;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.visium.fieldservice.R;
import com.visium.fieldservice.ui.dialog.FragmentPhotosMaquina;
import com.visium.fieldservice.ui.dialog.PhtotosFragment;
import com.visium.fieldservice.ui.dialog.PostEditDialogFragment;
import com.visium.fieldservice.ui.dialog.PostPontoEditDialogFragment;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;

import static com.visium.fieldservice.verifier.Verifier.prompt;

/**
 * Created by ltonon on 02/12/2016.
 */

public class Verifier {
    private static int[] postList = new int[5];
    private static int postCount = 0;
    private static int lastNumber = 0;
    private static boolean initialized = false;
    private static long lastGeoCode = -1;

    private static long rollBackGeoCode = -1;
    private static int rollBackNumber = 0;
    //private static boolean enabledToEdit = false;


    public static void setStartingNumber(int number) {
        lastNumber = number;
        postCount = 0;
        initialized = true;


        //FIXME
        rollBackNumber = number;
        //setEnabledToEdit(false);
    }
    /*public static void setEnabledToEdit(boolean enabled) {
        enabledToEdit = enabled;
    }*/

    public static void addPostCount(long geoCode) {
        if(geoCode != lastGeoCode) {
            setRollBackStack(geoCode, lastNumber);
            ++postCount;
            lastGeoCode = geoCode;
        }
    }

    public static void setRollBackStack(long geoCode, int number) {
        rollBackGeoCode = geoCode;
        rollBackNumber = number;
    }

    public static void clearRollBackStack() {
        rollBackGeoCode = -1;
    }

    public static void rollBack(long geoCode) {
        if(geoCode == rollBackGeoCode) {
            --postCount;
            lastNumber = rollBackNumber;
            lastGeoCode = -1;
            clearRollBackStack();
        }
    }

     public static void prompt(final Context c) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(c);
        alert.setTitle("Digite o número da próxima foto");
        final EditText input = new EditText(c);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        //input.setText("0");
        input.requestFocus();
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);

                if (input.getText().toString().equals("")) {
                    Toast.makeText(alert.getContext(), "Digite um numero", Toast.LENGTH_LONG).show();
                } else {
                final int number = Integer.valueOf(input.getText().toString());
                if (initialized && lastNumber != number) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(c);
                    alert.setMessage("O último número da sequência foi \"" + (lastNumber - 1) + "\" e o digitado \"" + number
                            + "\"\nDeseja quebrar a sequência?");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            setStartingNumber(number);
                        }
                    });
                    alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            prompt(c);
                        }
                    });
                    alert.show();
                } else {
                    setStartingNumber(number);
                }
            }
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });
        alert.show();
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void promptAndAdd(final long geoCode, final PhtotosFragment a)    {
        AlertDialog.Builder alert = new AlertDialog.Builder(a.getContext());
        alert.setTitle("Digite o número da próxima foto");
        final EditText input = new EditText(a.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        //input.setText("0");
        input.requestFocus();
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) a.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                final int number = Integer.valueOf(input.getText().toString());
                if(initialized && lastNumber != number) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(a.getContext());
                    alert.setMessage("O último número da sequência foi \""+(lastNumber-1)+"\" e o digitado \""+number
                            +"\"\nDeseja quebrar a sequência?");
                    alert.setTitle("Quebra de sequência");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            setStartingNumber(number);
                            a.addImage(a.getContext(), String.format("%04d", getNextNumber(geoCode, a)), true);
                        }
                    });
                    alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            promptAndAdd(geoCode, a);
                        }
                    });
                    alert.show();
                } else {
                    setStartingNumber(number);
                    a.addImage(a.getContext(), String.format("%04d", getNextNumber(geoCode, a)), true);
                }
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) a.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });
        alert.show();
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) a.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void promptAndAdd(final long geoCode, final PostEditDialogFragment a)    {
        AlertDialog.Builder alert = new AlertDialog.Builder(a.getContext());
        alert.setTitle("Digite o número da próxima foto");
        final EditText input = new EditText(a.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        //input.setText("0");
        input.requestFocus();
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) a.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                final int number = Integer.valueOf(input.getText().toString());
                if(initialized && lastNumber != number) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(a.getContext());
                    alert.setMessage("O último número da sequência foi \""+(lastNumber-1)+"\" e o digitado \""+number
                            +"\"\nDeseja quebrar a sequência?");
                    alert.setTitle("Quebra de sequência");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            setStartingNumber(number);
                            a.addImage(a.getContext(), String.format("%04d", getNextNumber(geoCode, a)), true);
                        }
                    });
                    alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            promptAndAdd(geoCode, a);
                        }
                    });
                    alert.show();
                } else {
                    setStartingNumber(number);
                    a.addImage(a.getContext(), String.format("%04d", getNextNumber(geoCode, a)), true);
                }
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) a.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });
        alert.show();
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) a.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void promptAndAdd(final long geoCode, final FragmentPhotosMaquina a)    {
        AlertDialog.Builder alert = new AlertDialog.Builder(a.getContext());
        alert.setTitle("Digite o número da próxima foto");
        final EditText input = new EditText(a.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        //input.setText("0");
        input.requestFocus();
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) a.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                final int number = Integer.valueOf(input.getText().toString());
                if(initialized && lastNumber != number) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(a.getContext());
                    alert.setMessage("O último número da sequência foi \""+(lastNumber-1)+"\" e o digitado \""+number
                            +"\"\nDeseja quebrar a sequência?");
                    alert.setTitle("Quebra de sequência");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            setStartingNumber(number);
                            a.addImage(a.getContext(), String.format("%04d", getNextNumber(geoCode, a)), true);
                        }
                    });
                    alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            promptAndAdd(geoCode, a);
                        }
                    });
                    alert.show();
                } else {
                    setStartingNumber(number);
                    a.addImage(a.getContext(), String.format("%04d", getNextNumber(geoCode, a)), true);
                }
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) a.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });
        alert.show();
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) a.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void promptAndAddPonto(final long geoCode, final PostPontoEditDialogFragment a)    {
        AlertDialog.Builder alert = new AlertDialog.Builder(a.getContext());
        alert.setTitle("Digite o número da próxima foto");
        final EditText input = new EditText(a.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setRawInputType(Configuration.KEYBOARD_12KEY);
        //input.setText("0");
        input.requestFocus();
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) a.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);

                if (input.getText().toString().equals("")) {
                    Toast.makeText(a.getContext(), "Digite um numero", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                final int number = Integer.valueOf(input.getText().toString());

                if (initialized && lastNumber != number) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(a.getContext());
                    alert.setMessage("O último número da sequência foi \"" + (lastNumber - 1) + "\" e o digitado \"" + number
                            + "\"\nDeseja quebrar a sequência?");
                    alert.setTitle("Quebra de sequência");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            setStartingNumber(number);
                            a.addImage(a.getContext(), String.format("%04d", getPontoNextNumber(geoCode, a)), true);
                        }
                    });
                    alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            promptAndAddPonto(geoCode, a);
                        }
                    });
                    alert.show();
                } else {
                    setStartingNumber(number);
                    a.addImage(a.getContext(), String.format("%04d", getPontoNextNumber(geoCode, a)), true);
                }
            }
            }
        });
        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                InputMethodManager imm = (InputMethodManager) a.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
            }
        });
        alert.show();
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) a.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static int getNextNumber(long geoCode, PostEditDialogFragment a) {
        if(geoCode != rollBackGeoCode) {
            setRollBackStack(geoCode, lastNumber);
        }
        if(postCount > 4 || !initialized) {
            promptAndAdd(geoCode, a);
            return -1;
        }
        return lastNumber++;
    }

    public static int getNextNumber(long geoCode, PhtotosFragment a) {
        if(geoCode != rollBackGeoCode) {
            setRollBackStack(geoCode, lastNumber);
        }
        if(postCount > 4 || !initialized) {
            promptAndAdd(geoCode, a);
            return -1;
        }
        return lastNumber++;
    }

    public static int getNextNumber(long geoCode, FragmentPhotosMaquina a) {
        if(geoCode != rollBackGeoCode) {
            setRollBackStack(geoCode, lastNumber);
        }
        if(postCount > 4 || !initialized) {
            promptAndAdd(geoCode, a);
            return -1;
        }
        return lastNumber++;
    }

    public static int getPontoNextNumber(long geoCode, PostPontoEditDialogFragment a) {
        if(geoCode != rollBackGeoCode) {
            setRollBackStack(geoCode, lastNumber);
        }
        if(postCount > 4 || !initialized) {
            promptAndAddPonto(geoCode, a);
            return -1;
        }
        return lastNumber++;
    }

}
