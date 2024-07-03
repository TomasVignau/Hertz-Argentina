package hertz;

import java.util.Comparator;

public class CompararVendedores implements Comparator<Vendedor> {

    @Override
    public int compare(Vendedor v1, Vendedor v2) {
        if (v1.getCantRecaudada() > v2.getCantRecaudada()) {
            return -1;
        } else if (v1.getCantRecaudada() > v2.getCantRecaudada()) {
            return 0;
        } else {
            return 1;
        }
    }
}
