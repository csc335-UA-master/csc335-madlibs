package alex;

public class MadlibsController {

	private MadlibsModel model;
	
	public MadlibsController()
	{
		this.model = new MadlibsModel();
	}
	
	public boolean gameOver()
	{
		return false;
	}
	
	
	public void makeGuess(int position, String replacement) throws MadlibsIllegalPOSException
	{
		this.model.replace(position, replacement);
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
