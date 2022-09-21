package edu.yu.introtoalgs;

/** Implements the TransportationState interface.
 *
 *
 * Students may ONLY use the specified constructor, and may (perhaps even
 * encouraged to) add as many other methods as they choose.
 *
 * @author Avraham Leff
 */

import java.util.Objects;

import static edu.yu.introtoalgs.TransportationState.Location.*;

public class TransportationStateImpl implements TransportationState {
    private int mithiumAtSrc;
    private int cathiumAtSrc;
    private Location truckLocation;
    private int totalMithium;
    private int totalCathium;

    /** Constructor:
     *
     * @param mithiumAtSrc amount of mithium at the src location, must be >= 0
     * @param cathiumAtSrc amount of cathium at the src location, must be >= 0
     * @param truckLocation location of the truck, must not be null
     * @param totalMithium sum of mithium amounts at src + dest, must be > 0
     * @param totalCathium sum of cathium amounts at src + dest, must be > 0
     *
     * @Students: you may NOT USE ANY OTHER CONSTRUCTOR SIG
     */
    public TransportationStateImpl(final int mithiumAtSrc, final int cathiumAtSrc, final Location truckLocation, final int totalMithium, final int totalCathium) {
        if ((mithiumAtSrc < 0) || (cathiumAtSrc < 0) || (truckLocation == null) || (totalMithium <= 0) || (totalCathium <=0)){
            throw new IllegalArgumentException();
        }
        this.mithiumAtSrc = mithiumAtSrc;
        this.cathiumAtSrc = cathiumAtSrc;
        this.truckLocation = truckLocation;
        this.totalMithium = totalMithium;
        this.totalCathium = totalCathium;
    } // constructor


    @Override
    public int getMithiumSrc() { return this.mithiumAtSrc; }

    @Override
    public int getCathiumSrc() { return this.cathiumAtSrc; }

    @Override
    public int getMithiumDest() { return (this.totalMithium - this.mithiumAtSrc); }

    @Override
    public int getCathiumDest() { return (this.totalCathium - this.cathiumAtSrc); }

    @Override
    public Location truckLocation() { return this.truckLocation; }

    @Override
    public int getTotalMithium() { return this.totalMithium; }

    @Override
    public int getTotalCathium() { return this.totalCathium; }

    @Override
    public String toString() {
        return "[" + mithiumAtSrc + " kg of mithium and " + cathiumAtSrc + " kg of cathium at src.\n" + getMithiumDest() + " kg of mithium and " + getCathiumDest() + " kg of cathium at dest.\nThe truck is parked at the " + truckLocation + "]\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportationStateImpl that = (TransportationStateImpl) o;
        return mithiumAtSrc == that.mithiumAtSrc && cathiumAtSrc == that.cathiumAtSrc && totalMithium == that.totalMithium && totalCathium == that.totalCathium && truckLocation == that.truckLocation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mithiumAtSrc, cathiumAtSrc, truckLocation, totalMithium, totalCathium);
    }
}   // class