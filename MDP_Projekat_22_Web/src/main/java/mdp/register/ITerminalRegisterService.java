package mdp.register;

import java.math.BigInteger;

import mdp.exceptions.TerminalNotFoundException;
import mdp.register.dtos.CreateTerminalDto;
import mdp.register.dtos.GetTerminalDto;
import mdp.register.dtos.UpdateTerminalDto;

public interface ITerminalRegisterService {
	public GetTerminalDto[] getTerminals();

	public GetTerminalDto createTerminal(CreateTerminalDto dto);

	public GetTerminalDto updateTerminal(UpdateTerminalDto dto) throws TerminalNotFoundException;

	public void deleteTerminal(BigInteger id) throws TerminalNotFoundException;

	public GetTerminalDto getTerminal(BigInteger passageId, boolean isCustomsStep, String terminalName)
			throws TerminalNotFoundException;
}
