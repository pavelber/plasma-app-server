package fac2db.level;

import fac2db.record.LevelRecord;


public interface ILevelState 
{
    int getVnl();
    int getParity();
    int getBaseIndex();
    int getNumber();
    double getEnergy();
    boolean hasEnergy();
    int getStatisticalWeight();
    String getComplexName();
    String getNonRelativsticName();
    String getRelativsticName();
    int getElementNumber();
    int getIonNumber();
    int getProjectNumber();
    LevelRecord toRecord();
    String toString();
    boolean isSelected();
    
    String getConfiguration();

	void setConfiguration(String configuration) ;
	
    int getCrmNumber();
    LevelState setCrmNumber(final int num);

    
    LevelState setVnl(final int iVnl);
    LevelState setParity(final int iParity);
    LevelState setBaseIndex(final int iIndex);
    LevelState setNumber(final int iNumber);
    LevelState setEnergy(final double dEnergy);
    LevelState setStatisticalWeight(final int iWeight);
    LevelState setComplexName(final String sName);
    LevelState setNonRelativsticName(final String sName);
    LevelState setRelativsticName(final String sName);
    LevelState setElementNumber(final int iIndex);
    LevelState setIonNumber(final int iIndex);
    LevelState setProjectNumber(final int iIndex);
    LevelState setSelected(boolean flag);
}
