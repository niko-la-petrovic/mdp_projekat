package mdp.register;

import java.math.BigInteger;
import java.util.List;

import mdp.exceptions.TerminalNotFoundException;
import mdp.models.CustomsTerminal;
import mdp.models.dtos.CreateTerminalDto;
import mdp.models.dtos.UpdateTerminalDto;

public interface TerminalRegister {
	public List<CustomsTerminal> getTerminals();

	public CustomsTerminal createTerminal(CreateTerminalDto dto);

	public CustomsTerminal updateTerminal(UpdateTerminalDto dto) throws TerminalNotFoundException;

	public void deleteTerminal(BigInteger id) throws TerminalNotFoundException;

	public CustomsTerminal getTerminal(BigInteger passageId, boolean isCustomsStep, String terminalName)
			throws TerminalNotFoundException;
}
