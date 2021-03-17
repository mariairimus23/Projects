library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity kb_main is
	port (Clk: in std_logic;
	Start: in std_logic;
	Rst: in std_logic;
	Ps2Clk: in std_logic;
	Ps2Data: in std_logic; 
    Anod: out std_logic_vector (3 downto 0);
    Catod: out std_logic_vector (7 downto 0);
    Tx: out std_logic);
end kb_main;

architecture Behavioral of kb_main is

--debounce
signal Rst_out: std_logic;

--filters
signal Ps2Clk_out: std_logic;
signal Ps2Data_out: std_logic;

--shift_register
signal PL: std_logic;
signal PL_out: std_logic;
signal key_input: std_logic;
signal key_output: std_logic_vector(10 downto 0);

--data register
signal key_data: std_logic_vector(7 downto 0);
signal ascii_data: std_logic_vector(7 downto 0);

--uart register
signal Start_out: std_logic;

--ascii register
signal display_data: std_logic_vector(7 downto 0);
signal data: std_logic_vector(7 downto 0);

--break
signal Break: std_logic;

--shift_display
signal shifted_display: std_logic_vector(31 downto 0);

--uart
signal TxRdy: std_logic;

begin

    ---------------------------------------------------------------------
    --                     Debouncere si Filtere 
    ---------------------------------------------------------------------
    
    -- debouncer pentru butonul Rst
	 debounce_rst: entity WORK.debounce port map(
	     Clk => Clk,
	     Data_in => Rst,
	     Data_out => Rst_out);
	     
	-- debouncer pentru butonul Start
	 debounce_start: entity WORK.debounce port map(
	     Clk => Clk,
	     Data_in => Start,
	     Data_out => Start_out);
	 	
	-- filter pentru clk keybord 
	filtering_clk: entity WORK.filtering port map(
	            Clk => Clk, 
	            Data_in => Ps2Clk, 
	            Data_out => Ps2Clk_out);
	           
	-- filter pentru data keyboard
	filtering_data: entity WORK.filtering port map(
	            Clk => Clk, 
	            Data_in => Ps2Data, 
	            Data_out => Ps2Data_out);
	            
	---------------------------------------------------------------------
	--                     Bistabil si Registrii
	---------------------------------------------------------------------
		   
	-- bistabil D pentru intarziere dintre registre => mentinerea datelor
	flipflop_D: entity WORK.flipflop_D port map (
	     Clk => Clk, 
	     D => PL, 
		 Q => PL_out);
		 
    -- serial input de pe keyboard
	shift_register: entity WORK.shift_register port map(
	       Clk => Ps2Clk_out, 
	       Rst => Rst_out, 
	       PL => PL_out, 
	       input => Ps2Data_out, 
	       enable => PL, 
		   Q => key_output);
		   
	-- registru care memoreaza datele
	data_register: entity WORK.data_register port map (
	    Clk => Clk, 
        Rst => Rst_out, 
        PL => PL, 
        input_data => key_output(8 downto 1), 
        Q => key_data);
        
    -- registru care pastreaza codurile ascii
	ascii_register: entity WORK.data_register port map (
	    Clk => Clk, 
        Rst => Rst_out, 
        PL => '1', 
        input_data => ascii_data, 
        Q => data);
     
    ---------------------------------------------------------------------
    --                  Decodificator pentru Keys
    ---------------------------------------------------------------------
	
	-- decodificator catre transforma datele primite de pe keys in coduri ascii
	key_to_ascii: entity WORK.key_to_ascii port map (
	   keycode => key_data,
       ascii_code => ascii_data);
	
	---------------------------------------------------------------------
	
	-- break signal to signalize when to stop
	Break <= '1' when key_data = x"F0" else '0';
	
	---------------------------------------------------------------------
	--                  Componente pentru Display
	---------------------------------------------------------------------
	
	--componenta pentru shiftarea pe display a carcterelor afisate
    shift_display : entity WORK.shift_display port map (
        Clk => Break,
        Rst => Rst_out,
        Input => data,
        Q => shifted_display);
     
    --afisor cu 7 segmente    
    display : entity WORK.display port map (
        Clk => Clk, 
        Input => shifted_display, 
		Anod => Anod, 
		Catod => Catod);
    
    ---------------------------------------------------------------------
    --                         Componenta UART
    ---------------------------------------------------------------------
    
    --uart pentru transmiterea seriala catre PC
    uart_tx: entity WORK.uart_tx port map (
        Clk => Clk,
        Rst => Rst_out,
        TxData => data,
        Start => Start_out,
        Tx => Tx,
        TxRdy => TxRdy);
    
    ----------------------------------------------------------------------

end Behavioral;