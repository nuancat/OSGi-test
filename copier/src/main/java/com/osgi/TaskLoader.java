package com.osgi;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import static com.osgi.TaskLoader.UrlParameters.*;

/**
 * Загрузчик конфигураций для операций копирования, управляется посредством флага <code>isRunning</code>
 * и таймаутом между сканированиями файла с задачами <code>timeoutBetweenTasksLoading</code>. При инициализации
 * конструктора задается начальный файл где хранятся задания на копирование
 */
public class TaskLoader extends AbstractHandler {
    /**
     * Логгер
     */
    final Logger logger = LoggerFactory.getLogger(TaskLoader.class);

    /**
     * Класс содержащий параметры при HTTP запросах
     */
    public static class UrlParameters {
        public static final String SOURCE_FOLDER = "source";
        public static final String DESTINATION_FOLDER = "destination";
        public static final String FILE_MASK = "mask";
        public static final String REQUEST_PATH = "/addTask";
    }

    public TaskLoader() {
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest req, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        try {
            if (s.equals(REQUEST_PATH)) {
                CopierTaskModel ctm = loadTask(request.getParameterMap());
                executeTask(ctm);
                httpServletResponse.getWriter().println("Task successfully executed");
                logger.warn("Task successfully executed");
            }
        } catch (NullPointerException e) {
            logger.warn("Adding task failed");
            httpServletResponse.getWriter().println("Adding task failed");
        } catch (Exception exc) {
            logger.warn(exc.getMessage());
            httpServletResponse.getWriter().println(exc.getMessage());
        }
        request.setHandled(true);
    }

    /**
     * Преобразует задачи из Мап в объект
     *
     * @param parameterMap приходящие в виде запроса задачи
     * @return объект задачи
     * @throws NullPointerException если какого то либо параметра нет, пробрасывается ошибка
     */
    public CopierTaskModel loadTask(Map<String, String[]> parameterMap) throws NullPointerException {
        String[] source = parameterMap.get(SOURCE_FOLDER);
        String[] destination = parameterMap.get(DESTINATION_FOLDER);
        String[] mask = parameterMap.get(FILE_MASK);
        return new CopierTaskModel(source[0], destination[0], mask[0]);
    }

    /**
     * Поиск и копирование файлов
     *
     * @param ctm объект задачи
     * @throws Exception если что-то пошло не так
     */
    public void executeTask(CopierTaskModel ctm) throws Exception {
        Set<Path> paths = Copier.findFilesInSourceWithTask(ctm);
        Copier.makeCopy(paths, ctm.getDestinationFolder());

    }
}
