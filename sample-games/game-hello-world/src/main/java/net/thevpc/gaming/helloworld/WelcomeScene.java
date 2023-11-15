package net.thevpc.gaming.helloworld;


import net.thevpc.gaming.atom.annotations.AtomScene;
import net.thevpc.gaming.atom.annotations.OnInit;
import net.thevpc.gaming.atom.model.Orientation;
import net.thevpc.gaming.atom.model.ViewBox;
import net.thevpc.gaming.atom.presentation.Alignment;
import net.thevpc.gaming.atom.presentation.DefaultScene;
import net.thevpc.gaming.atom.presentation.components.SButton;
import net.thevpc.gaming.atom.presentation.components.SceneComponentState;
import net.thevpc.gaming.atom.presentation.components.TextStyle;
import net.thevpc.gaming.atom.presentation.layers.FillScreenGradientLayer;

import java.awt.*;

@AtomScene(
        id = "welcome",
        sceneEngine = "welcome",
        title = "balls",
        tileWidth = 600,
        tileHeight = 400
)

public class WelcomeScene extends DefaultScene {

    private final SButton start = new SButton("start", "Press space to start...");

    public WelcomeScene() {

    }



    @OnInit
    public void onInstall() {
        // Fill Background with Gradient colored Layer
        addLayer(new FillScreenGradientLayer(new Color(200, 230, 126), Color.DARK_GRAY, Orientation.NORTH_EAST));



        addComponent(start);



    }


}
