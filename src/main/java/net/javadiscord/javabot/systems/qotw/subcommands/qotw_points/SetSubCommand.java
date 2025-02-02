package net.javadiscord.javabot.systems.qotw.subcommands.qotw_points;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
import net.javadiscord.javabot.Bot;
import net.javadiscord.javabot.command.Responses;
import net.javadiscord.javabot.command.SlashCommandHandler;
import net.javadiscord.javabot.systems.qotw.dao.QuestionPointsRepository;

import java.sql.SQLException;

public class SetSubCommand implements SlashCommandHandler {
    @Override
    public ReplyAction handle(SlashCommandEvent event) {
        var memberOption = event.getOption("user");
        var pointsOption = event.getOption("points");
        if (memberOption == null || pointsOption == null) {
            return Responses.error(event, "Missing required arguments.");
        }
        var member = memberOption.getAsMember();
        var memberId = member.getIdLong();
        var points = pointsOption.getAsLong();
        try (var con = Bot.dataSource.getConnection()) {
            var repo = new QuestionPointsRepository(con);
            repo.update(memberId, points);
            return Responses.success(event,
                    "Set QOTW-Points",
                    String.format("Successfully changed the points of %s to %s", member.getUser().getAsMention(), points));
        } catch (SQLException e) {
            e.printStackTrace();
            return Responses.error(event, "An Error occurred.");
        }

    }
}


