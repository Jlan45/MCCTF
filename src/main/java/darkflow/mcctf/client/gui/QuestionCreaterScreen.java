package darkflow.mcctf.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import darkflow.mcctf.gui.QuestionCreaterScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class QuestionCreaterScreen extends HandledScreen<ScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");

    public QuestionCreaterScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, null,title);
    }
    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        TextFieldWidget textfield=new TextFieldWidget(textRenderer, 10, 10, 100, 20, Text.of("my_textfield_text"));
        ButtonWidget myButton = new ButtonWidget(10,20, 100, 100, Text.of("my_button_text"), button -> {
            ClientPlayNetworking.send(Testmod.TEST_DATA_NAME, PacketByteBufs.create().writeString(textfield.getText()));
        });
        PageTurnWidget nextButton=new PageTurnWidget(10,40,true, button -> {
            ClientPlayNetworking.send(Testmod.TEST_DATA_NAME, PacketByteBufs.create().writeString(textfield.getText()));
        },true);
//		this.addDrawableChild(myButton);
        this.addDrawableChild(nextButton);

        this.addDrawableChild(textfield);
    }
}
