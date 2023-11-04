package darkflow.mcctf.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class TestGUI extends Screen {
    public TestGUI() {
        // 此参数为屏幕的标题，进入屏幕中，复述功能会复述。
        super(Text.literal("我的教程屏幕"));
    }


    public ButtonWidget button1;
    public ButtonWidget button2;

    @Override
    public void init() {
        button1 = new ButtonWidget(width / 2 - 205, 20, 200, 20, Text.literal("按钮 1"), button -> {
            System.out.println("你点击了按钮 1！");
        });
        button2 = new ButtonWidget(width / 2 + 5, 20, 200, 20, Text.literal("按钮 2"), button -> {
            System.out.println("你点击了按钮 2！");
        });

        addDrawableChild(button1);
        addDrawableChild(button2);
    }
}