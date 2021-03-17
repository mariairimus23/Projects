library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity data_register is
  Port (Clk: in std_logic;
        Rst: in std_logic;
        PL: in std_logic;
        input_data: in std_logic_vector(7 downto 0);
        Q: out std_logic_vector(7 downto 0));
end data_register;

architecture Behavioral of data_register is
signal temp_Q: std_logic_vector(7 downto 0) := (others => '0');

begin

process(Clk, Rst, PL, input_data)
begin
    if(Rst = '1') then
        temp_Q <= (others => '0');
    elsif(Rising_Edge(Clk)) then 
        if(PL = '1') then
            temp_Q <= input_data;
        end if;
    end if;
end process;

Q <= temp_Q;

end Behavioral;