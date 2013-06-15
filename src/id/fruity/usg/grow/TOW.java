package id.fruity.usg.grow;

public class TOW {
	private static final TOWData DEFAULT_DATA = new TOWData(TOWCoefficient.UK,
			TOWCoefficient.UK.getMedianMaternalHeight(),
			TOWCoefficient.UK.getMedianMaternalWeight(), 0, TOWData.UNKNOWN);

	public static float getDefaultTOW() {
		return getTOW(DEFAULT_DATA);
	}

	public static float getTOW(TOWData data) {
		return data.getConstantContribution()
				+ data.getMaternalHeightContribution()
				+ data.getMaternalWeightContribution()
				+ data.getEthnicContribution() + data.getParityContribution()
				+ data.getSexContribution();
	}
}
