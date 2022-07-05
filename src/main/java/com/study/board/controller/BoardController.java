package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //127.0.0.1:8090/board/write
    public String boardWriteForm(){

        return "boardwrite";
    }

    @PostMapping("/board/writepro") //127.0.0.1:8090/board/writepro
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{ //작성한 글 db로 보내기

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");


        return "message";
    }

    @GetMapping("/board/list") //127.0.0.1:8090/board/list
    public String boardList(Model model){

        model.addAttribute("list", boardService.boardList());
        return "boardlist"; //template 담당하는 html 이름
    }

    @GetMapping("/board/view") // localhost:8090/board/view?id=1
    public String boardView(Model model, Integer id){

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete") // localhost:8090/board/delete?id=1
    public String boardDelete(Integer id){

        boardService.boardDelete(id);

        return "redirect:/board/list"; //삭제 후 list 창으로 넘어감
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){
        //modify/{id}의 id 부분이 @PathVariable에서 인식이 돼서 integer 형식으로 들어감


        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardView(id); //id에서 보드를 가지고 오고 새로 가져온 내용을 덮어씌우기
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";
    }
}

