package hxasjc.jdaslash;

import hxasjc.jdaslash.annotations.JdaSlashCommand;
import hxasjc.jdaslash.annotations.JdaSlashCommandSetGuildOnly;
import hxasjc.jdaslash.annotations.SlashCommandCompileException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public class AnnotatedCommandCompiler {
    private final Reflections reflections;
    private final JDA jda;
    private final SlashCommandClient globalCommandClient;
    private final Map<Long, SlashCommandClient> guildCommandClients = new HashMap<>();

    public AnnotatedCommandCompiler(JDA jda) {
        this.jda = jda;
        globalCommandClient = new SlashCommandClient(jda);

        reflections = new Reflections();
    }

    public void compileCommands() {
        /*Set<Class<?>> commandClasses = reflections
                .get(SubTypes.of(TypesAnnotated.with(JdaSlashCommand.class)).asClass());*/

        for (Class<?> clazz : new Reflections().getTypesAnnotatedWith(JdaSlashCommand.class)) {
            System.out.println(clazz.getName());
        }

        /*Iterable<Class<?>> commandClasses = ClassIndex.getAnnotated(JdaSlashCommand.class);

        commandClasses.forEach(clazz -> System.out.println(clazz.getName()));*/
    }

    public void compileClass(Class<? extends SlashCommand> clazz) {
        if (!clazz.isAnnotationPresent(JdaSlashCommand.class)) {
            throw new SlashCommandCompileException("Class '" + clazz.getSimpleName() + "' does not have the JdaSlashCommand annotation");
        }

        JdaSlashCommand jdaSlashCommand = clazz.getAnnotation(JdaSlashCommand.class);
        SlashCommandData data = Commands.slash(jdaSlashCommand.name(), jdaSlashCommand.description());

        if (clazz.isAnnotationPresent(JdaSlashCommandSetGuildOnly.class)) {
            data.setGuildOnly(true);
        }
    }
}
