package alex;

public class MadlibsController {

	private MadlibsModel model;
	
	public MadlibsController()
	{
		this.model = new MadlibsModel();
	}
	
	public boolean gameOver()
	{
		return model.getGuesses() == model.getMaxPosition();
	}
	
	
	public void makeGuess(int position, String replacement) throws MadlibsIllegalPOSException
	{
		if(this.model.getPOSMap().get(replacement.toLowerCase()).toUpperCase().equals(this.model.getPOS(position)))
		{
			this.model.replace(position, replacement);
		}
		else
		{
			throw new MadlibsIllegalPOSException("Bad POS");
		}
	}
	
	public String getTemplate()
	{
		return this.model.getTemplateString();
	}
	public String getPOS(int position) throws MadlibsIllegalPositionException
	{
		if (position < 1)
		{
			throw new MadlibsIllegalPositionException("Bad Position");
		}
		return this.model.getPOS(position);
	}
	
	
}
