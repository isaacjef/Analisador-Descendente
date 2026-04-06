# Analisador-Descendente



id+id*id$

E -> XT -> XYF -> XYid = XY

+id*id$

XY -> Xnull -> X -> XT+ = XT

id*id$

XT -> XYF -> XYid = XY

*id$

XY -> XYF* = XYF

id$

XYF -> XYid = XY

$

XY -> Xnull -> X


 [E], [X,T], [X,Y,F], [X,Y,id], [X,Y],
 [X,null], [X], [X, T, +], [X, T], [X, Y, F],
 [X, Y, id], [X, Y], [X, Y, F, *], [X, Y, F],
 [X, Y, id], [X, Y], [X, null], [X], [null], []
 

 ---------------
 
 var json = "{ \"terminal\": [\"+\", \"*\", \"(\", \")\", \"id\", \"$\"],";
    json += " \"nonterminal\": [\"E\", \"X\", \"T\", \"Y\", \"F\"],";
    json += " \"grammar\": {\"E\": [\"TX\"], \"X\": [\"+TX\", \"null\"], \"T\": [\"FY\"], \"Y\": [\"*FY\", \"null\"], \"F\": [\"(E)\", \"id\"]},";
    json += " \"first\": {\"E\": [\"(\", \"id\"], \"X\": [\"+\", \"null\"], \"T\": [\"(\", \"id\"], \"Y\": [\"*\", \"null\"], \"F\": [\"(\", \"id\"]},";
    json += " \"follow\": {\"E\": [\")\", \"$\"], \"X\": [\")\", \"$\"], \"T\": [\"+\", \")\", \"$\"], \"Y\": [\"+\", \")\", \"$\"], \"F\": [\"*\", \"+\", \")\", \"$\"]},";