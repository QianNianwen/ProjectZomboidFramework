package top.qiannianwen.pzf;

import java.io.File;
import java.io.IOException;

/**
 * TODO
 *
 * @author 朱海洋 1799426163@qq.com
 * @version 1.0.0
 */
public class MainScreenState {

    static {
        try {
            final File file = new File("D:\\Test\\test.txt");
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            System.exit(0);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        zombie.gameStates.MainScreenState.main(args);
    }

}
