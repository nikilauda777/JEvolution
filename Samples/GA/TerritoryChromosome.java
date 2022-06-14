//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import evSOLve.JEvolution.*;

import evSOLve.JEvolution.chromosomes.Chromosome;
import evSOLve.JEvolution.chromosomes.IntChromosome;
import evSOLve.JEvolution.misc.Utilities;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;

public class TerritoryChromosome extends IntChromosome {

    protected int length1;

    public TerritoryChromosome() {
        this(10);
    }

    public TerritoryChromosome(int var1) {
        super(var1);
        this.length1 = var1;
    }

    public TerritoryChromosome(Element var1) {
        super(var1);
    }

    public void doMutation() {
        for(int var1 = 0; var1 < this.length1; ++var1) { // 0 to 100
            if (Utilities.nextReal() < this.mutationRate) {
                int var2 = Utilities.nextIntegerInRange(0, this.length1 - 1);
                Utilities.swap(this.bases, var1, var2);
            }
        }

    }

    public void doCrossover(Chromosome var1) {
        if (Utilities.nextReal() < this.crossoverRate) {
            List var2 = (List)var1.getBases();
            List var3 = this.bases;
            List var4 = Utilities.getList();
            var4.clear();
            var4.addAll(var3);
            int var5 = Utilities.nextIntegerInRange(0, this.length1);
            int var6 = Utilities.nextIntegerInRange(0, this.length1);
            int var7;
            if (var6 < var5) {
                var7 = var5;
                var5 = var6;
                var6 = var7;
            }

            Integer var8;
            int var9;
            for(var7 = var5; var7 < var6; ++var7) {
                var8 = (Integer)var2.get(var7);
                var9 = var3.indexOf(var8);
                Utilities.swap(var3, var7, var9);
            }

            for(var7 = var5; var7 < var6; ++var7) {
                var8 = (Integer)var4.get(var7);
                var9 = var2.indexOf(var8);
                Utilities.swap(var2, var7, var9);
            }
        }

    }

    public void mixSoup() {
        if (this.bases == null) {
            this.bases = new ArrayList(this.length);
        } else {
            this.bases.clear();
        }

        int var1;
        switch(this.soupType) {
            case 0:
                var1 = 0;
                break;
            case 1:
                var1 = Utilities.nextIntegerInRange(0, this.length1 - 1);
                break;
            default:
                return;
        }

        int var2;
        for(var2 = 0; var2 < this.length1; ++var2) {
            this.bases.add(var2);
        }

        for(var2 = 0; var2 < this.length1 / 2; ++var2) {
            int var3 = Utilities.nextIntegerInRange(0, this.length1 - 1 - var1);
            int var4 = Utilities.nextIntegerInRange(var1, this.length1 - 1);
            Utilities.swap(this.bases, var3, var4);
        }

    }
}
