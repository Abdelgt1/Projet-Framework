package mvc2;

import java.util.List;
import java.util.Vector;

public class ListeAnomalies {
    private Vector<Anomalie> listeAnomalies = new Vector<>();

    public Anomalie getAnomalie(int i) {
        return listeAnomalies.get(i);
    }

    public Anomalie getAnomalieById(int id) {
        for (Anomalie anomalie : listeAnomalies) {
            if (anomalie.getId() == id) {
                return anomalie;
            }
        }
        return null;
    }
    


    public int getNbAnomalie() {
        return listeAnomalies.size();
    }

    public void addAnomalie(Anomalie a) {
        int id = a.getId();
        for (int i = 0; i < getNbAnomalie(); i++) {
            if (getAnomalie(i).getId() == id) {
                listeAnomalies.remove(i);
                listeAnomalies.add(a);
                return;
            }
        }
        listeAnomalies.add(a);
    }

    public void deleteAnomalie(int id) {
        for (int i = 0; i < getNbAnomalie(); i++) {
            if (getAnomalie(i).getId() == id) {
                listeAnomalies.remove(i);
                return;
            }
        }
    }

    
    public List<Anomalie> getAnomalies() {
        return new Vector<>(listeAnomalies);
    }
}
