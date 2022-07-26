/**
 * TerminalRegisterService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mdp.register.terminals;

public interface TerminalRegisterService extends java.rmi.Remote {
	public mdp.register.terminals.dtos.GetCustomsTerminalDto[] getTerminalsStartingWithName(java.lang.String namePrefix)
			throws java.rmi.RemoteException;

	public mdp.register.terminals.dtos.GetCustomsTerminalDto searchTerminalSimulation(java.lang.String terminalName,
			java.math.BigInteger passageId, boolean isEntry) throws java.rmi.RemoteException;

	public mdp.register.terminals.dtos.GetCustomsTerminalDto createTerminal(
			mdp.register.terminals.dtos.CreateTerminalDto dto) throws java.rmi.RemoteException;

	public mdp.register.terminals.dtos.GetCustomsTerminalDto[] getTerminals() throws java.rmi.RemoteException;

	public mdp.register.terminals.dtos.GetCustomsTerminalDto searchTerminal(mdp.dtos.SearchTerminalDto dto)
			throws java.rmi.RemoteException, mdp.exceptions.TerminalNotFoundException;

	public void deleteTerminal(java.math.BigInteger id)
			throws java.rmi.RemoteException, mdp.exceptions.TerminalNotFoundException;

	public mdp.register.terminals.dtos.GetCustomsTerminalDto getTerminal(java.math.BigInteger passageId,
			boolean isCustomsStep, java.lang.String terminalName)
			throws java.rmi.RemoteException, mdp.exceptions.TerminalNotFoundException;

	public mdp.register.terminals.dtos.GetCustomsTerminalDto updateTerminal(
			mdp.register.terminals.dtos.UpdateTerminalDto dto) throws java.rmi.RemoteException;
}
