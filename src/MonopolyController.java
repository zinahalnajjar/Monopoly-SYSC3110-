import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Connects the model to the view
 *
 * @author Tooba
 */
public class MonopolyController implements ActionListener {

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
