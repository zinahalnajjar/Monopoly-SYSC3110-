import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Connects the model to the view
 *
 * @author Tooba
 */
public class MonopolyController implements ActionListener, Serializable {

    final Game model;
    public MonopolyController(Game model){
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        System.out.println(command);

        model.run(command);
    }
}
