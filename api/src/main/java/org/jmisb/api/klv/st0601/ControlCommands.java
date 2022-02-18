package org.jmisb.api.klv.st0601;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;

/**
 * Control Commands.
 *
 * <p>This class represents any number (zero or more) of {@code ControlCommand} packs that may occur
 * within an ST 0601 Local Set. Unlike most of the entries supported in ST 0601, {@code
 * ControlCommand} can repeat.
 */
public class ControlCommands implements IUasDatalinkValue, INestedKlvValue, ISpecialFraming {

    private List<ControlCommand> controlCommands = new ArrayList<>();

    /**
     * Create from a single command.
     *
     * @param command the command to add.
     */
    public ControlCommands(ControlCommand command) {
        controlCommands.add(command);
    }

    /**
     * Create from multiple commands.
     *
     * @param commands the commands to add.
     */
    public ControlCommands(List<ControlCommand> commands) {
        this.controlCommands.addAll(commands);
    }

    @Override
    public String getDisplayableValue() {
        return "[Control Commands]";
    }

    @Override
    public final String getDisplayName() {
        return "Control Commands";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        int i = tag.getIdentifier();
        for (ControlCommand controlCommand : controlCommands) {
            if (controlCommand.getCommandId() == i) {
                return controlCommand;
            }
        }
        return null;
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        Set<ControlCommandsKey> identifiers = new TreeSet<>();
        for (ControlCommand controlCommand : controlCommands) {
            identifiers.add(new ControlCommandsKey(controlCommand.getCommandId()));
        }
        return identifiers;
    }

    @Override
    public byte[] getBytes() {
        throw new UnsupportedOperationException("use getEncodedValue()");
    }

    /**
     * Append a control command to the set of commands.
     *
     * @param controlCommand the command to add.
     */
    public void add(ControlCommand controlCommand) {
        controlCommands.add(controlCommand);
    }

    @Override
    public byte[] getEncodedValue() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (ControlCommand controlCommand : controlCommands) {
            arrayBuilder.appendAsOID(UasDatalinkTag.ControlCommand.getCode());
            byte[] bytes = controlCommand.getBytes();
            arrayBuilder.appendAsBerLength(bytes.length);
            arrayBuilder.append(bytes);
        }
        return arrayBuilder.toBytes();
    }
}
