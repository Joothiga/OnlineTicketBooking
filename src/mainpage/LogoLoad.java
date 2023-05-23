package mainpage;

import java.io.*;

@SuppressWarnings("serial")
public class LogoLoad extends Throwable {
    public LogoLoad(String msg)
    {
        super(msg);
    }
    public static boolean logoLoading(String filePath) throws LogoLoad, IOException {
        File logo = new File(filePath);
        boolean load = false;
            if(logo.exists())
            {
                load = true;
                FileInputStream fileinput = new FileInputStream(logo);
                int temp;
                while((temp = fileinput.read()) != -1)
                {
                    System.out.print((char)temp);
                }
                fileinput.close();
            }
            else
            {
                throw new LogoLoad("Logo loading issues occurred, Please try again");
            }
        return load;
    }
}
