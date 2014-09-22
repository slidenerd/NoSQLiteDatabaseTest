
package slidenerd.vivz.model;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Score")
public class Score extends Model implements Serializable{
    @Column(name = "scorePhysics")
    public int scorePhysics;
    @Column(name = "scoreChemistry")
    public int scoreChemistry;
    @Column(name = "scoreMaths")
    public int scoreMaths;
    @Column(name = "scoreBiology")
    public int scoreBiology;

    public Score()
    {
        super();
    }

    public Score(int scorePhysics,
            int scoreChemistry,
            int scoreMaths,
            int scoreBiology)
    {
        super();
        this.scorePhysics = scorePhysics;
        this.scoreChemistry = scoreChemistry;
        this.scoreMaths = scoreMaths;
        this.scoreBiology = scoreBiology;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "P: "
                + scorePhysics
                + " C: "
                + scoreChemistry
                + " M: "
                + scoreMaths
                + " B: "
                + scoreBiology;
    }
}
